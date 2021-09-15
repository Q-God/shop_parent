package com.gmall.payment.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.gmall.entity.PaymentInfo;
import com.gmall.enums.PaymentStatus;
import com.gmall.payment.config.AliPayConfig;
import com.gmall.payment.service.PaymentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @version v1.0
 * @ClassName PaymentController
 * @Description TODO
 * @Author Q
 */
@RestController
@RequestMapping("/payment")
public class PaymentInfoController {

    @Autowired
    private PaymentInfoService paymentInfoService;

    //1.创建支付二维码
    @RequestMapping("createQrCode/{orderId}")
    public String createOrCode(@PathVariable Long orderId) throws AlipayApiException {
        return paymentInfoService.createOrCode(orderId);
    }

    //2.支付宝异步调用接口
    @PostMapping("/notify/async")
    public String asyncNotify(@RequestParam Map<String, String> aliParamMap) throws AlipayApiException {
        //调用SDK验证签名
        boolean signVerified = AlipaySignature.rsaCheckV1(aliParamMap, AliPayConfig.alipay_public_key, AliPayConfig.charset, AliPayConfig.sign_type);
        if (signVerified) {
            String tradeStatus = aliParamMap.get("trade_status");
            //交易成功
            if ("TRADE_SUCCESS".equals(tradeStatus) || "TRADE_FINISHED".equals(tradeStatus)) {
                //根据商户订单号查询支付订单信息
                String outTradeNo = aliParamMap.get("out_trade_no");
                //本地支付订单信息
                PaymentInfo paymentInfo = paymentInfoService.getPaymentInfo(outTradeNo);
                //如果我的本地支付订单信息是已经关闭和已经支付 支付宝就不需要再次调用
                if (paymentInfo.getPaymentStatus().equals(PaymentStatus.ClOSED.name()) ||
                        paymentInfo.getPaymentStatus().equals(PaymentStatus.PAID.name())) {
                    return "failure";
                }
                //修改支付订单状态
                paymentInfoService.updatePaymentInfo(aliParamMap);
                return "success";
            }

        } else {
            // TODO 验签失败则记录异常日志，并在response中返回failure.
            return "failure";
        }
        return "fiture";
    }

    //3.发起退款接口
    @RequestMapping("/refund/{orderId}")
    public boolean refund(@PathVariable Long orderId) throws AlipayApiException {
        return paymentInfoService.refund(orderId);
    }

    //4.查询支付宝中是否有交易记录
    @RequestMapping("/selectAlipayTrade/{orderId}")
    public boolean selectAlipayTrade(@PathVariable Long orderId) throws AlipayApiException {
        return paymentInfoService.selectAlipayTrade(orderId);
    }

    //5.关闭交易
    @GetMapping("/closeAlipayTrade/{orderId}")
    public boolean closeAlipayTrade(@PathVariable Long orderId) throws AlipayApiException {
        return paymentInfoService.closeAlipayTrade(orderId);
    }

    //6.查询支付订单信息
    @GetMapping("getPaymentInfo/{outTradeNo}")
    public PaymentInfo getPaymentInfo(@PathVariable String outTradeNo) {
        return paymentInfoService.getPaymentInfo(outTradeNo);
    }

}
