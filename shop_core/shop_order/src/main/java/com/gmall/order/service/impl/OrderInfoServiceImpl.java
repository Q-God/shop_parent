package com.gmall.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gmall.cart.client.CartFeignClient;
import com.gmall.entity.CartInfo;
import com.gmall.entity.OrderDetail;
import com.gmall.entity.OrderInfo;
import com.gmall.entity.UserAddress;
import com.gmall.enums.OrderStatus;
import com.gmall.enums.ProcessStatus;
import com.gmall.order.mapper.OrderDetailMapper;
import com.gmall.order.mapper.OrderInfoMapper;
import com.gmall.order.service.OrderInfoService;
import com.gmall.product.client.ProductFeignClient;
import com.gmall.user.client.UserFeignClient;
import com.gmall.util.common.util.IdWorker;
import com.gmall.utils.core.constant.MqConst;
import com.gmall.utils.core.constant.RedisConst;
import com.gmall.utils.core.executor.MyExecutor;
import org.joda.time.DateTime;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @version v1.0
 * @ClassName OrderInfoServiceImpl
 * @Description TODO
 * @Author Q
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    //远程调用
    @Autowired
    private CartFeignClient cartFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${cancel.order.delay}")
    private Integer cancelOrderDelay;

    /**
     * 用户确认页面
     *
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> confirm(String userId) {
        List<CartInfo> checkedCartList = cartFeignClient.getCheckedCart(userId);
        List<UserAddress> userAddressList = userFeignClient.getAddressByUserId(userId);
        Map<String, Object> resultMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(checkedCartList)) {
            List<OrderDetail> orderDetailList = checkedCartList.stream().map(cartInfo ->
            {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setSkuId(cartInfo.getSkuId());
                orderDetail.setSkuName(cartInfo.getSkuName());
                orderDetail.setImgUrl(cartInfo.getImgUrl());
                orderDetail.setSkuNum(cartInfo.getSkuNum().toString());
                orderDetail.setOrderPrice(cartInfo.getCartPrice());
                //计算订单总金额
                return orderDetail;
            }).collect(Collectors.toList());
            BigDecimal totalPrice = orderDetailList.stream().map(orderDetail -> orderDetail.getOrderPrice().multiply(new BigDecimal(orderDetail.getSkuNum())
            )).reduce(BigDecimal.ZERO, BigDecimal::add);
            int totalNum = orderDetailList.stream().mapToInt(orderDetail -> Integer.parseInt(orderDetail.getSkuNum())).sum();


            resultMap.put("detailArrayList", orderDetailList);

            resultMap.put("totalMoney", totalPrice);
            resultMap.put("totalNum", totalNum);
        }

        resultMap.put("userAddressList", userAddressList);
        String tradeNo = generateTradeNo(userId);
        resultMap.put("tradeNo", tradeNo);
        return resultMap;
    }

    @Override
    public Long saveOrderAndDetail(OrderInfo orderInfo) {
        //保存订单基本信息，提交后应为待支付
        orderInfo.setOrderStatus(OrderStatus.UNPAID.name());
        //保存订单编号
        String orderNo = idWorker.generateOrderNo();
        orderInfo.setOutTradeNo(orderNo);
        //订单主体信息
        orderInfo.setTradeBody("我的购物商品");
        orderInfo.setCreateTime(DateTime.now().toDate());
        //设置订单支付过期时间 设置的是一天 默认一般30
        orderInfo.setExpireTime(DateTime.now().plusDays(1).toDate());
        //订单进程状态
        orderInfo.setProcessStatus(ProcessStatus.UNPAID.name());
        //商品的总价格 orderInfo.setTotalMoney(new BigDecimal(0.01));
        orderInfo.setTotalMoney(orderInfo.getTotalMoney());
        orderInfoMapper.insert(orderInfo);
        //b.保存订单详情信息
        List<OrderDetail> orderDetailList = orderInfo.getOrderDetailList();
        orderDetailList.stream().forEach(orderDetail -> {
            orderDetail.setOrderId(orderInfo.getId());
        });
        //该商品详情属于哪个订单
        orderDetailMapper.insertBatchSomeColumn(orderDetailList);
        //发送一个延迟消息 定时取消订单
        rabbitTemplate.convertAndSend(MqConst.CANCEL_ORDER_EXCHANGE,
                MqConst.CANCEL_ORDER_ROUTE_KEY,
                orderInfo.getId(),
                correlationData -> {
                    correlationData.getMessageProperties().setDelay(cancelOrderDelay);
                    return correlationData;
                });
        return orderInfo.getId();
    }

    @Override
    public String generateTradeNo(String userId) {
        String tradeNo = idWorker.nextId() + "";
        //把流水号放到redis中
        String tradeKey = RedisConst.USER_KEY_PREFIX + userId + ":tradeNo";
        redisTemplate.opsForValue().set(tradeKey, tradeNo);
        return tradeNo;
    }

    @Override
    public boolean checkTradeNo(String tradeNo, String userId) {
        String tradeNokey = RedisConst.USER_KEY_PREFIX + userId + ":tradeNo";
        String redisTradeNo = (String) redisTemplate.opsForValue().get(tradeNokey);
        return tradeNo.equals(redisTradeNo);
    }

    @Override
    public void deleteTradeNo(String userId) {
        String tradeNokey = RedisConst.USER_KEY_PREFIX + userId + ":tradeNo";
        redisTemplate.delete(tradeNokey);
    }

    @Override
    public List<String> checkStockAndPrice(String userId, OrderInfo orderInfo) {
        //新建一个list用于保存警告信息
        List<String> warningInfoList = new ArrayList<>();
        List<OrderDetail> orderDetailList = orderInfo.getOrderDetailList();
        //异步编排的集合
        List<CompletableFuture> mulitFutureList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(orderDetailList)) {
            orderDetailList.stream().forEach(orderDetail -> {
                //看每个商品库存是否足够 调用接口
                //http://localhost:8100/hasStock?skuId=24&num=99
                Long skuId = orderDetail.getSkuId();
                String skuNum = orderDetail.getSkuNum();

                CompletableFuture<Void> stockCompletableFuture = CompletableFuture.runAsync(() -> {
                    //String result = HttpClientUtil.doGet("http://localhost:8100/hasStock?skuId=" + skuId + "&num=" + skuNum);
                    String result = "1";
                    //0:无库存   1:有库存
                    if ("0".equals(result)) {
                        warningInfoList.add(orderDetail.getSkuName() + "库存不足");
                    }
                }, MyExecutor.getInstance());
                mulitFutureList.add(stockCompletableFuture);
                CompletableFuture<Void> priceCompletableFuture = CompletableFuture.runAsync(() -> {
                    //验证价格是否是最新价格
                    BigDecimal skuPrice = productFeignClient.getSkuPrice(orderDetail.getSkuId());
                    //判断当前送货清单里面的商品价格与实时价格
                    if (orderDetail.getOrderPrice().compareTo(skuPrice) != 0) {

                        warningInfoList.add(orderDetail.getSkuName() + "价格有变化!");
                        //更新缓存里面的价格信息
                        cartFeignClient.queryFromDbToRedis(userId);

                    }
                }, MyExecutor.getInstance());
                mulitFutureList.add(priceCompletableFuture);
            });
        }
        //需要两个同时昨晚才能往下走
        CompletableFuture[] mulitFutureArray = new CompletableFuture[mulitFutureList.size()];
        CompletableFuture.allOf(mulitFutureList.toArray(mulitFutureArray)).join();
        return warningInfoList;
    }

    /**
     * 获取订单
     *
     * @param orderInfoId
     * @return
     */
    @Override
    public OrderInfo getOrderInfoById(Long orderInfoId) {
        return orderInfoMapper.selectById(orderInfoId);
    }

    /**
     * 更新订单信息
     *
     * @param orderInfo
     */
    @Override
    public void updateOrderInfo(OrderInfo orderInfo) {
        orderInfoMapper.updateById(orderInfo);
    }

    /**
     * 更新订单状态
     *
     * @param orderInfo
     * @param processStatus
     */
    @Override
    public void updateOrderStatus(OrderInfo orderInfo, ProcessStatus processStatus) {
        //修改订单状态
        orderInfo.setOrderStatus(processStatus.getOrderStatus().name());
        orderInfo.setProcessStatus(processStatus.name());
        updateOrderInfo(orderInfo);
    }

    /**
     * 根据OrderID获取订单信息
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderInfo getOrderInfo(Long orderId) {
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        if (!ObjectUtils.isEmpty(orderInfo)) {
            LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper();
            wrapper.eq(OrderDetail::getOrderId, orderId);
            List<OrderDetail> orderDetailList = orderDetailMapper.selectList(wrapper);
            orderInfo.setOrderDetailList(orderDetailList);
        }
        return orderInfo;
    }

    /**
     * 通知仓库修改库存
     *
     * @param orderInfo
     */
    @Override
    public void sendMagToWareHouse(OrderInfo orderInfo) {
        //将订单修改为已通知仓库
        updateOrderStatus(orderInfo, ProcessStatus.NOTIFIED_WARE);
        //拼接传递消息参数json数据
        Map<String, Object> dataMap = assembleWareHouseData(orderInfo);
        String dataMapJson = JSON.toJSONString(dataMap);
        //发送消息给仓库系统
        rabbitTemplate.convertAndSend(MqConst.DECREASE_STOCK_EXCHANGE, MqConst.DECREASE_STOCK_ROUTE_KEY, dataMapJson);
    }


    @Override
    public String splitOrder(Long orderId, String wareHouseIdSkuIdMap) {
        //获取原订单信息
        OrderInfo orderInfo = getOrderInfo(orderId);
        if (!ObjectUtils.isEmpty(orderInfo)) {
            //将传过来的订单信息进行解析
            List<Map> wareOrderList = JSONObject.parseArray(wareHouseIdSkuIdMap, Map.class);
            //封装子订单信息
            List<Map> childOrderInfoList = new ArrayList<>();
            wareOrderList.stream().forEach(wareOrder -> {
                String wareHouseId = (String) wareOrder.get("wareHouseId");
                List<String> skuIdList = (List<String>) wareOrder.get("skuIdList");
                OrderInfo childOrderInfo = new OrderInfo();

                BeanUtils.copyProperties(orderInfo, childOrderInfo);
                childOrderInfo.setWareHouseId(wareHouseId);
                childOrderInfo.setParentOrderId(orderId);
                childOrderInfo.setId(null);

                List<OrderDetail> orderDetailList = orderInfo.getOrderDetailList();

                List<OrderDetail> childOrderDetailList = new ArrayList<>();
                BigDecimal[] totalMoney = {new BigDecimal(0)};
                orderDetailList.stream().forEach(orderDetail -> {
                    if (skuIdList.contains(orderDetail.getId())) {
                        childOrderDetailList.add(orderDetail);
                        //设置子订单总金额
                        BigDecimal orderPrice = orderDetail.getOrderPrice();
                        String skuNum = orderDetail.getSkuNum();
                        totalMoney[0] = totalMoney[0].add(orderPrice.multiply(new BigDecimal(skuNum)));
                    }
                });

                //设置子订单基本信息的订单详情信息
                childOrderInfo.setOrderDetailList(childOrderDetailList);
                //设置子订单的总价
                childOrderInfo.setTotalMoney(totalMoney[0]);
                //d.保存子订单
                saveOrderAndDetail(childOrderInfo);
                //把子订单转换为json字符串
                Map<String, Object> dataMap = assembleWareHouseData(childOrderInfo);
                childOrderInfoList.add(dataMap);
            });
            //e.修改原始订单状态
            updateOrderStatus(orderInfo, ProcessStatus.SPLIT);
            //f.返回数据给库存系统
            return JSONObject.toJSONString(childOrderInfoList);
        }
        return null;
    }

    private Map<String, Object> assembleWareHouseData(OrderInfo orderInfo) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("orderId", orderInfo.getId());
        dataMap.put("consignee", orderInfo.getConsignee());
        dataMap.put("consigneeTel", orderInfo.getConsigneeTel());
        dataMap.put("orderComment", orderInfo.getOrderComment());
        dataMap.put("orderBody", orderInfo.getTradeBody());
        dataMap.put("deliveryAddress", orderInfo.getDeliveryAddress());
        dataMap.put("paymentWay", 2);
        //TODO 这里要添加一个非常重要的字段
        dataMap.put("wareId", orderInfo.getWareHouseId());
        //商品清单
        List<OrderDetail> orderDetailList = orderInfo.getOrderDetailList();
        List<Map> orderDetailMapList = new ArrayList<>();

        orderDetailList.stream().forEach(orderDetail -> {
            Map<String, Object> orderDetailMap = new HashMap<>();
            orderDetailMap.put("skuId", orderDetail.getSkuId());
            orderDetailMap.put("skuNum", orderDetail.getSkuNum());
            orderDetailMap.put("skuName", orderDetail.getSkuName());
            orderDetailMapList.add(orderDetailMap);
        });

        dataMap.put("details", orderDetailMapList);
        //然后把map转换为json字符串
        //  String jsonString = JSON.toJSONString(dataMap);
        return dataMap;
    }

}
