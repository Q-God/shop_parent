package com.gmall.order.service;

import com.gmall.entity.OrderInfo;
import com.gmall.enums.ProcessStatus;

import java.util.List;
import java.util.Map;

public interface OrderInfoService {
    /**
     * 用户确认页面
     *
     * @param userId
     * @return
     */
    Map<String, Object> confirm(String userId);

    /**
     * 保存订单信息
     *
     * @param orderInfo 封装订单信息对象
     * @return 订单id
     */
    Long saveOrderAndDetail(OrderInfo orderInfo);

    /**
     * 生成流水号
     *
     * @param userId
     * @return
     */
    String generateTradeNo(String userId);

    /**
     * @param tradeNo
     * @param userId
     * @return
     */
    boolean checkTradeNo(String tradeNo, String userId);

    /**
     * @param userId
     */
    void deleteTradeNo(String userId);

    /**
     * @param userId
     * @param orderInfo
     * @return
     */
    List<String> checkStockAndPrice(String userId, OrderInfo orderInfo);

    /**
     * 获取订单
     *
     * @param orderInfoId
     * @return
     */
    OrderInfo getOrderInfoById(Long orderInfoId);

    /**
     * 更新订单信息
     *
     * @param orderInfo
     */
    void updateOrderInfo(OrderInfo orderInfo);

    /**
     * 更新订单状态
     *
     * @param orderInfo
     * @param processStatus
     */
    void updateOrderStatus(OrderInfo orderInfo, ProcessStatus processStatus);

    /**
     * 根据OrderID获取订单信息
     *
     * @param orderId
     * @return
     */
    OrderInfo getOrderInfo(Long orderId);

    /**
     * 通知仓库修改库存
     *
     * @param orderInfo
     */
    void sendMagToWareHouse(OrderInfo orderInfo);

    /**
     * 拆单功能
     *
     * @param orderId
     * @param wareHouseIdSkuIdMap
     * @return
     */
    String splitOrder(Long orderId, String wareHouseIdSkuIdMap);
}
