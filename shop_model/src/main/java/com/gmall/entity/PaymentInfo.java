package com.gmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 支付信息表
 * </p>
 *
 * @author 尚硅谷张强
 * @since 2021-07-31
 */
@Accessors(chain = true)
@TableName("payment_info")
public class PaymentInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 对外业务编号
     */
    private String outTradeNo;

    /**
     * 订单编号
     */
    private String orderId;

    /**
     * 支付类型（微信 支付宝）
     */
    private String paymentType;

    /**
     * 交易编号
     */
    private String tradeNo;

    /**
     * 支付金额
     */
    private BigDecimal paymentMoney;

    /**
     * 交易内容
     */
    private String paymentContent;

    /**
     * 支付状态
     */
    private String paymentStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 回调时间
     */
    private Date callbackTime;

    /**
     * 回调信息
     */
    private String callbackContent;

    public PaymentInfo() {
    }


    public Integer getId() {
        return this.id;
    }

    public String getOutTradeNo() {
        return this.outTradeNo;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public String getPaymentType() {
        return this.paymentType;
    }

    public String getTradeNo() {
        return this.tradeNo;
    }

    public BigDecimal getPaymentMoney() {
        return this.paymentMoney;
    }

    public String getPaymentContent() {
        return this.paymentContent;
    }

    public String getPaymentStatus() {
        return this.paymentStatus;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public Date getCallbackTime() {
        return this.callbackTime;
    }

    public String getCallbackContent() {
        return this.callbackContent;
    }

    public PaymentInfo setId(Integer id) {
        this.id = id;
        return this;
    }

    public PaymentInfo setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
        return this;
    }

    public PaymentInfo setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    public PaymentInfo setPaymentType(String paymentType) {
        this.paymentType = paymentType;
        return this;
    }

    public PaymentInfo setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
        return this;
    }

    public PaymentInfo setPaymentMoney(BigDecimal paymentMoney) {
        this.paymentMoney = paymentMoney;
        return this;
    }

    public PaymentInfo setPaymentContent(String paymentContent) {
        this.paymentContent = paymentContent;
        return this;
    }

    public PaymentInfo setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
        return this;
    }

    public PaymentInfo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public PaymentInfo setCallbackTime(Date callbackTime) {
        this.callbackTime = callbackTime;
        return this;
    }

    public PaymentInfo setCallbackContent(String callbackContent) {
        this.callbackContent = callbackContent;
        return this;
    }

    public String toString() {
        return "PaymentInfo(id=" + this.getId() + ", outTradeNo=" + this.getOutTradeNo() + ", orderId=" + this.getOrderId() + ", paymentType=" + this.getPaymentType() + ", tradeNo=" + this.getTradeNo() + ", paymentMoney=" + this.getPaymentMoney() + ", paymentContent=" + this.getPaymentContent() + ", paymentStatus=" + this.getPaymentStatus() + ", createTime=" + this.getCreateTime() + ", callbackTime=" + this.getCallbackTime() + ", callbackContent=" + this.getCallbackContent() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof PaymentInfo)) return false;
        final PaymentInfo other = (PaymentInfo) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$outTradeNo = this.getOutTradeNo();
        final Object other$outTradeNo = other.getOutTradeNo();
        if (this$outTradeNo == null ? other$outTradeNo != null : !this$outTradeNo.equals(other$outTradeNo))
            return false;
        final Object this$orderId = this.getOrderId();
        final Object other$orderId = other.getOrderId();
        if (this$orderId == null ? other$orderId != null : !this$orderId.equals(other$orderId)) return false;
        final Object this$paymentType = this.getPaymentType();
        final Object other$paymentType = other.getPaymentType();
        if (this$paymentType == null ? other$paymentType != null : !this$paymentType.equals(other$paymentType))
            return false;
        final Object this$tradeNo = this.getTradeNo();
        final Object other$tradeNo = other.getTradeNo();
        if (this$tradeNo == null ? other$tradeNo != null : !this$tradeNo.equals(other$tradeNo)) return false;
        final Object this$paymentMoney = this.getPaymentMoney();
        final Object other$paymentMoney = other.getPaymentMoney();
        if (this$paymentMoney == null ? other$paymentMoney != null : !this$paymentMoney.equals(other$paymentMoney))
            return false;
        final Object this$paymentContent = this.getPaymentContent();
        final Object other$paymentContent = other.getPaymentContent();
        if (this$paymentContent == null ? other$paymentContent != null : !this$paymentContent.equals(other$paymentContent))
            return false;
        final Object this$paymentStatus = this.getPaymentStatus();
        final Object other$paymentStatus = other.getPaymentStatus();
        if (this$paymentStatus == null ? other$paymentStatus != null : !this$paymentStatus.equals(other$paymentStatus))
            return false;
        final Object this$createTime = this.getCreateTime();
        final Object other$createTime = other.getCreateTime();
        if (this$createTime == null ? other$createTime != null : !this$createTime.equals(other$createTime))
            return false;
        final Object this$callbackTime = this.getCallbackTime();
        final Object other$callbackTime = other.getCallbackTime();
        if (this$callbackTime == null ? other$callbackTime != null : !this$callbackTime.equals(other$callbackTime))
            return false;
        final Object this$callbackContent = this.getCallbackContent();
        final Object other$callbackContent = other.getCallbackContent();
        if (this$callbackContent == null ? other$callbackContent != null : !this$callbackContent.equals(other$callbackContent))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PaymentInfo;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $outTradeNo = this.getOutTradeNo();
        result = result * PRIME + ($outTradeNo == null ? 43 : $outTradeNo.hashCode());
        final Object $orderId = this.getOrderId();
        result = result * PRIME + ($orderId == null ? 43 : $orderId.hashCode());
        final Object $paymentType = this.getPaymentType();
        result = result * PRIME + ($paymentType == null ? 43 : $paymentType.hashCode());
        final Object $tradeNo = this.getTradeNo();
        result = result * PRIME + ($tradeNo == null ? 43 : $tradeNo.hashCode());
        final Object $paymentMoney = this.getPaymentMoney();
        result = result * PRIME + ($paymentMoney == null ? 43 : $paymentMoney.hashCode());
        final Object $paymentContent = this.getPaymentContent();
        result = result * PRIME + ($paymentContent == null ? 43 : $paymentContent.hashCode());
        final Object $paymentStatus = this.getPaymentStatus();
        result = result * PRIME + ($paymentStatus == null ? 43 : $paymentStatus.hashCode());
        final Object $createTime = this.getCreateTime();
        result = result * PRIME + ($createTime == null ? 43 : $createTime.hashCode());
        final Object $callbackTime = this.getCallbackTime();
        result = result * PRIME + ($callbackTime == null ? 43 : $callbackTime.hashCode());
        final Object $callbackContent = this.getCallbackContent();
        result = result * PRIME + ($callbackContent == null ? 43 : $callbackContent.hashCode());
        return result;
    }
}
