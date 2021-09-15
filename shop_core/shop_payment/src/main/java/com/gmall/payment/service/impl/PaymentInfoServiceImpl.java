package com.gmall.payment.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundApplyRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundApplyResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gmall.entity.OrderInfo;
import com.gmall.entity.PaymentInfo;
import com.gmall.enums.PaymentStatus;
import com.gmall.enums.PaymentType;
import com.gmall.order.client.OrderFeignClient;
import com.gmall.payment.config.AliPayConfig;
import com.gmall.payment.mapper.PaymentInfoMapper;
import com.gmall.payment.service.PaymentInfoService;
import com.gmall.utils.core.constant.MqConst;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @version v1.0
 * @ClassName PaymentInfoServiceImpk
 * @Description TODO
 * @Author Q
 */
@Service
public class PaymentInfoServiceImpl implements PaymentInfoService {

    @Autowired
    private PaymentInfoMapper paymentInfoMapper;

    @Autowired
    private OrderFeignClient orderFeignClient;

    @Autowired
    private AlipayClient alipayClient;

    @Autowired
    private AlipayTradePagePayRequest request;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 创建付款二维码
     *
     * @param orderId
     * @return
     */
    @Override
    public String createOrCode(Long orderId) throws AlipayApiException {
        //1.根据订单id查询订单信息
        OrderInfo orderInfo = orderFeignClient.getOrderInfo(orderId);
        //2.保存支付信息
        savePaymentInfo(orderInfo);
        //3.调用支付宝返回二维码支付的接口
        //支付成功之后的同步通知
        request.setReturnUrl(AliPayConfig.return_payment_url);
        //支付成功之后的异步通知
        request.setNotifyUrl(AliPayConfig.notify_payment_url);
        //设置请求需要的参数
        Map<String, Object> requestParamMap = new HashMap<>();
        //商家订单号
        requestParamMap.put("out_trade_no", orderInfo.getOutTradeNo());
        requestParamMap.put("total_amount", orderInfo.getTotalMoney());
        //商品说明信息
        requestParamMap.put("subject", "买买买");
        requestParamMap.put("product_code", "FAST_INSTANT_TRADE_PAY");
        request.setBizContent(JSON.toJSONString(requestParamMap));
        AlipayTradePagePayResponse response = alipayClient.pageExecute(request);
        if (response.isSuccess()) {
            return response.getBody();
        }
        return "";
    }


    /**
     * 获取支付订单信息
     *
     * @param outTradeNo
     * @return
     */
    @Override
    public PaymentInfo getPaymentInfo(String outTradeNo) {
        LambdaQueryWrapper<PaymentInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PaymentInfo::getOutTradeNo, outTradeNo);
        wrapper.eq(PaymentInfo::getPaymentType, PaymentType.ALIPAY.name());
        return paymentInfoMapper.selectOne(wrapper);
    }

    /**
     * 跟新支付订单状态
     *
     * @param aliParamMap
     */
    @Override
    public void updatePaymentInfo(Map<String, String> aliParamMap) {
        String outTradeNo = aliParamMap.get("out_trade_no");
        PaymentInfo paymentInfo = getPaymentInfo(outTradeNo);
        paymentInfo.setPaymentStatus(PaymentStatus.PAID.name());
        paymentInfo.setCallbackTime(new Date());
        paymentInfo.setCallbackContent(aliParamMap.toString());
        //获取支付宝那边生成的订单号
        String tradeNo = aliParamMap.get("trade_no");
        paymentInfo.setTradeNo(tradeNo);
        paymentInfoMapper.updateById(paymentInfo);
        //发一条消息修改订单状态
        rabbitTemplate.convertAndSend(MqConst.PAY_ORDER_EXCHANGE, MqConst.PAY_ORDER_ROUTE_KEY, paymentInfo.getOrderId());
    }

    /**
     * 发起退款
     *
     * @param orderId
     * @return
     */
    @Override
    public boolean refund(Long orderId) throws AlipayApiException {
        AlipayTradeRefundApplyRequest request = new AlipayTradeRefundApplyRequest();
        OrderInfo orderInfo = orderFeignClient.getOrderInfo(orderId);
        //设置请求需要的参数
        Map<String, Object> requestParamMap = new HashMap<>();
        //商家订单号
        if (!ObjectUtils.isEmpty(orderInfo)) {
            requestParamMap.put("out_trade_no", orderInfo.getOutTradeNo());
            requestParamMap.put("refund_amount", orderInfo.getTotalMoney());
            //商品说明信息
            requestParamMap.put("refund_reason", "爆炸了，诈尸了");
            request.setBizContent(JSON.toJSONString(requestParamMap));
            AlipayTradeRefundApplyResponse response = alipayClient.execute(request);
            if (response.isSuccess()) {
                //如果退款成功修改支付订单状态为已关闭
                PaymentInfo paymentInfo = getPaymentInfo(orderInfo.getOutTradeNo());
                paymentInfo.setPaymentStatus(PaymentStatus.ClOSED.name());
                paymentInfoMapper.updateById(paymentInfo);
                return true;
            }
        }
        return false;
    }

    /**
     * 查询支付宝中是否有交易记录
     *
     * @param orderId
     * @return
     */
    @Override
    public boolean selectAlipayTrade(Long orderId) throws AlipayApiException {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        OrderInfo orderInfo = orderFeignClient.getOrderInfo(orderId);
        JSONObject bizContent = new JSONObject();
        if (!ObjectUtils.isEmpty(orderInfo)) {
            bizContent.put("out_trade_no", orderInfo.getOutTradeNo());
            request.setBizContent(bizContent.toString());
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            return response.isSuccess();
        }
        return false;
    }

    /**
     * 关闭交易
     *
     * @param orderId
     * @return
     * @throws AlipayApiException
     */
    @Override
    public boolean closeAlipayTrade(Long orderId) throws AlipayApiException {
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        OrderInfo orderInfo = orderFeignClient.getOrderInfo(orderId);
        JSONObject bizContent = new JSONObject();
        if (!ObjectUtils.isEmpty(orderInfo)) {
            bizContent.put("out_trade_no", orderInfo.getOutTradeNo());
            request.setBizContent(bizContent.toString());
            AlipayTradeCloseResponse response = alipayClient.execute(request);
            return response.isSuccess();
        }
        return false;
    }

    /**
     * 根据OrderId查询一个支付订单
     *
     * @param orderId
     * @return
     */
    @Override
    public PaymentInfo selectOne(Long orderId) {
        LambdaQueryWrapper<PaymentInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(PaymentInfo::getOrderId, orderId);
        return paymentInfoMapper.selectOne(wrapper);
    }

    /**
     * 更新支付订单信息
     *
     * @param paymentInfo
     */
    @Override
    public void update(PaymentInfo paymentInfo) {
        paymentInfoMapper.updateById(paymentInfo);
    }

    private void savePaymentInfo(OrderInfo orderInfo) {
        //先查查数据库中有没有
        LambdaQueryWrapper<PaymentInfo> wrapper = new LambdaQueryWrapper();
        wrapper.eq(PaymentInfo::getOrderId, orderInfo.getId());
        wrapper.eq(PaymentInfo::getPaymentType, PaymentType.ALIPAY.name());
        Integer count = paymentInfoMapper.selectCount(wrapper);
        if (count > 0) {
            return;
        }
        //没有就创建一个支付订单
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setOutTradeNo(orderInfo.getOutTradeNo());
        paymentInfo.setOrderId(orderInfo.getId() + "");
        paymentInfo.setPaymentType(PaymentType.ALIPAY.name());
        paymentInfo.setPaymentMoney(orderInfo.getTotalMoney());
        paymentInfo.setPaymentContent(orderInfo.getTradeBody());
        paymentInfo.setPaymentStatus(PaymentStatus.UNPAID.name());
        paymentInfo.setCreateTime(new Date());
        paymentInfoMapper.insert(paymentInfo);
    }
}
