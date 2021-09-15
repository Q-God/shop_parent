package com.gmall.payment.service;

import com.alipay.api.AlipayApiException;
import com.gmall.entity.PaymentInfo;

import java.util.Map;

public interface PaymentInfoService {
    /**
     * 创建付款二维码
     *
     * @param orderId
     * @return
     */
    String createOrCode(Long orderId) throws AlipayApiException;

    /**
     * 获取支付订单信息
     *
     * @param outTradeNo
     * @return
     */
    PaymentInfo getPaymentInfo(String outTradeNo);

    /**
     * 跟新支付订单状态
     *
     * @param aliParamMap
     */
    void updatePaymentInfo(Map<String, String> aliParamMap);


    /**
     * 发起退款
     *
     * @param orderId
     * @return
     */
    boolean refund(Long orderId) throws AlipayApiException;

    /**
     * @param orderId
     * @return
     */
    boolean selectAlipayTrade(Long orderId) throws AlipayApiException;

    /**
     * 关闭交易订单
     *
     * @param orderId
     * @return
     */
    boolean closeAlipayTrade(Long orderId) throws AlipayApiException;

    /**
     * 根据OrderId查询一个支付订单
     *
     * @param orderId
     * @return
     */
    PaymentInfo selectOne(Long orderId);

    /**
     * 更新支付订单信息
     *
     * @param paymentInfo
     */
    void update(PaymentInfo paymentInfo);
}
