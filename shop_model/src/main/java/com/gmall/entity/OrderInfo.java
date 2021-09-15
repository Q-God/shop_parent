package com.gmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单表 订单表
 * </p>
 *
 * @author 尚硅谷张强
 * @since 2021-07-30
 */
@Accessors(chain = true)
@TableName("order_info")
public class OrderInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 收货人
     */
    private String consignee;

    /**
     * 收件人电话
     */
    private String consigneeTel;

    /**
     * 总金额
     */
    private BigDecimal totalMoney;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 付款方式
     */
    private String paymentWay;

    /**
     * 送货地址
     */
    private String deliveryAddress;

    /**
     * 订单备注
     */
    private String orderComment;

    /**
     * 订单交易编号（第三方支付用)
     */
    private String outTradeNo;

    /**
     * 订单描述(第三方支付用)
     */
    private String tradeBody;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 失效时间
     */
    private Date expireTime;

    /**
     * 进度状态
     */
    private String processStatus;

    /**
     * 物流单编号
     */
    private String logisticsNum;

    /**
     * 父订单编号
     */
    private Long parentOrderId;

    /**
     * 图片路径
     */
    private String imgUrl;

    //订单明细集合
    @TableField(exist = false)
    private List<OrderDetail> orderDetailList;

    //仓库id 代表该商品在哪个仓库
    @TableField(exist = false)
    private String wareHouseId;

    public OrderInfo() {
    }


    public Long getId() {
        return this.id;
    }

    public String getConsignee() {
        return this.consignee;
    }

    public String getConsigneeTel() {
        return this.consigneeTel;
    }

    public BigDecimal getTotalMoney() {
        return this.totalMoney;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public Long getUserId() {
        return this.userId;
    }

    public String getPaymentWay() {
        return this.paymentWay;
    }

    public String getDeliveryAddress() {
        return this.deliveryAddress;
    }

    public String getOrderComment() {
        return this.orderComment;
    }

    public String getOutTradeNo() {
        return this.outTradeNo;
    }

    public String getTradeBody() {
        return this.tradeBody;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public Date getExpireTime() {
        return this.expireTime;
    }

    public String getProcessStatus() {
        return this.processStatus;
    }

    public String getLogisticsNum() {
        return this.logisticsNum;
    }

    public Long getParentOrderId() {
        return this.parentOrderId;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public List<OrderDetail> getOrderDetailList() {
        return this.orderDetailList;
    }

    public String getWareHouseId() {
        return this.wareHouseId;
    }

    public OrderInfo setId(Long id) {
        this.id = id;
        return this;
    }

    public OrderInfo setConsignee(String consignee) {
        this.consignee = consignee;
        return this;
    }

    public OrderInfo setConsigneeTel(String consigneeTel) {
        this.consigneeTel = consigneeTel;
        return this;
    }

    public OrderInfo setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
        return this;
    }

    public OrderInfo setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
        return this;
    }

    public OrderInfo setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public OrderInfo setPaymentWay(String paymentWay) {
        this.paymentWay = paymentWay;
        return this;
    }

    public OrderInfo setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        return this;
    }

    public OrderInfo setOrderComment(String orderComment) {
        this.orderComment = orderComment;
        return this;
    }

    public OrderInfo setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
        return this;
    }

    public OrderInfo setTradeBody(String tradeBody) {
        this.tradeBody = tradeBody;
        return this;
    }

    public OrderInfo setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public OrderInfo setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
        return this;
    }

    public OrderInfo setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
        return this;
    }

    public OrderInfo setLogisticsNum(String logisticsNum) {
        this.logisticsNum = logisticsNum;
        return this;
    }

    public OrderInfo setParentOrderId(Long parentOrderId) {
        this.parentOrderId = parentOrderId;
        return this;
    }

    public OrderInfo setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public OrderInfo setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
        return this;
    }

    public OrderInfo setWareHouseId(String wareHouseId) {
        this.wareHouseId = wareHouseId;
        return this;
    }

    public String toString() {
        return "OrderInfo(id=" + this.getId() + ", consignee=" + this.getConsignee() + ", consigneeTel=" + this.getConsigneeTel() + ", totalMoney=" + this.getTotalMoney() + ", orderStatus=" + this.getOrderStatus() + ", userId=" + this.getUserId() + ", paymentWay=" + this.getPaymentWay() + ", deliveryAddress=" + this.getDeliveryAddress() + ", orderComment=" + this.getOrderComment() + ", outTradeNo=" + this.getOutTradeNo() + ", tradeBody=" + this.getTradeBody() + ", createTime=" + this.getCreateTime() + ", expireTime=" + this.getExpireTime() + ", processStatus=" + this.getProcessStatus() + ", logisticsNum=" + this.getLogisticsNum() + ", parentOrderId=" + this.getParentOrderId() + ", imgUrl=" + this.getImgUrl() + ", orderDetailList=" + this.getOrderDetailList() + ", wareHouseId=" + this.getWareHouseId() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof OrderInfo)) return false;
        final OrderInfo other = (OrderInfo) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$consignee = this.getConsignee();
        final Object other$consignee = other.getConsignee();
        if (this$consignee == null ? other$consignee != null : !this$consignee.equals(other$consignee)) return false;
        final Object this$consigneeTel = this.getConsigneeTel();
        final Object other$consigneeTel = other.getConsigneeTel();
        if (this$consigneeTel == null ? other$consigneeTel != null : !this$consigneeTel.equals(other$consigneeTel))
            return false;
        final Object this$totalMoney = this.getTotalMoney();
        final Object other$totalMoney = other.getTotalMoney();
        if (this$totalMoney == null ? other$totalMoney != null : !this$totalMoney.equals(other$totalMoney))
            return false;
        final Object this$orderStatus = this.getOrderStatus();
        final Object other$orderStatus = other.getOrderStatus();
        if (this$orderStatus == null ? other$orderStatus != null : !this$orderStatus.equals(other$orderStatus))
            return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        final Object this$paymentWay = this.getPaymentWay();
        final Object other$paymentWay = other.getPaymentWay();
        if (this$paymentWay == null ? other$paymentWay != null : !this$paymentWay.equals(other$paymentWay))
            return false;
        final Object this$deliveryAddress = this.getDeliveryAddress();
        final Object other$deliveryAddress = other.getDeliveryAddress();
        if (this$deliveryAddress == null ? other$deliveryAddress != null : !this$deliveryAddress.equals(other$deliveryAddress))
            return false;
        final Object this$orderComment = this.getOrderComment();
        final Object other$orderComment = other.getOrderComment();
        if (this$orderComment == null ? other$orderComment != null : !this$orderComment.equals(other$orderComment))
            return false;
        final Object this$outTradeNo = this.getOutTradeNo();
        final Object other$outTradeNo = other.getOutTradeNo();
        if (this$outTradeNo == null ? other$outTradeNo != null : !this$outTradeNo.equals(other$outTradeNo))
            return false;
        final Object this$tradeBody = this.getTradeBody();
        final Object other$tradeBody = other.getTradeBody();
        if (this$tradeBody == null ? other$tradeBody != null : !this$tradeBody.equals(other$tradeBody)) return false;
        final Object this$createTime = this.getCreateTime();
        final Object other$createTime = other.getCreateTime();
        if (this$createTime == null ? other$createTime != null : !this$createTime.equals(other$createTime))
            return false;
        final Object this$expireTime = this.getExpireTime();
        final Object other$expireTime = other.getExpireTime();
        if (this$expireTime == null ? other$expireTime != null : !this$expireTime.equals(other$expireTime))
            return false;
        final Object this$processStatus = this.getProcessStatus();
        final Object other$processStatus = other.getProcessStatus();
        if (this$processStatus == null ? other$processStatus != null : !this$processStatus.equals(other$processStatus))
            return false;
        final Object this$logisticsNum = this.getLogisticsNum();
        final Object other$logisticsNum = other.getLogisticsNum();
        if (this$logisticsNum == null ? other$logisticsNum != null : !this$logisticsNum.equals(other$logisticsNum))
            return false;
        final Object this$parentOrderId = this.getParentOrderId();
        final Object other$parentOrderId = other.getParentOrderId();
        if (this$parentOrderId == null ? other$parentOrderId != null : !this$parentOrderId.equals(other$parentOrderId))
            return false;
        final Object this$imgUrl = this.getImgUrl();
        final Object other$imgUrl = other.getImgUrl();
        if (this$imgUrl == null ? other$imgUrl != null : !this$imgUrl.equals(other$imgUrl)) return false;
        final Object this$orderDetailList = this.getOrderDetailList();
        final Object other$orderDetailList = other.getOrderDetailList();
        if (this$orderDetailList == null ? other$orderDetailList != null : !this$orderDetailList.equals(other$orderDetailList))
            return false;
        final Object this$wareHouseId = this.getWareHouseId();
        final Object other$wareHouseId = other.getWareHouseId();
        if (this$wareHouseId == null ? other$wareHouseId != null : !this$wareHouseId.equals(other$wareHouseId))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof OrderInfo;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $consignee = this.getConsignee();
        result = result * PRIME + ($consignee == null ? 43 : $consignee.hashCode());
        final Object $consigneeTel = this.getConsigneeTel();
        result = result * PRIME + ($consigneeTel == null ? 43 : $consigneeTel.hashCode());
        final Object $totalMoney = this.getTotalMoney();
        result = result * PRIME + ($totalMoney == null ? 43 : $totalMoney.hashCode());
        final Object $orderStatus = this.getOrderStatus();
        result = result * PRIME + ($orderStatus == null ? 43 : $orderStatus.hashCode());
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $paymentWay = this.getPaymentWay();
        result = result * PRIME + ($paymentWay == null ? 43 : $paymentWay.hashCode());
        final Object $deliveryAddress = this.getDeliveryAddress();
        result = result * PRIME + ($deliveryAddress == null ? 43 : $deliveryAddress.hashCode());
        final Object $orderComment = this.getOrderComment();
        result = result * PRIME + ($orderComment == null ? 43 : $orderComment.hashCode());
        final Object $outTradeNo = this.getOutTradeNo();
        result = result * PRIME + ($outTradeNo == null ? 43 : $outTradeNo.hashCode());
        final Object $tradeBody = this.getTradeBody();
        result = result * PRIME + ($tradeBody == null ? 43 : $tradeBody.hashCode());
        final Object $createTime = this.getCreateTime();
        result = result * PRIME + ($createTime == null ? 43 : $createTime.hashCode());
        final Object $expireTime = this.getExpireTime();
        result = result * PRIME + ($expireTime == null ? 43 : $expireTime.hashCode());
        final Object $processStatus = this.getProcessStatus();
        result = result * PRIME + ($processStatus == null ? 43 : $processStatus.hashCode());
        final Object $logisticsNum = this.getLogisticsNum();
        result = result * PRIME + ($logisticsNum == null ? 43 : $logisticsNum.hashCode());
        final Object $parentOrderId = this.getParentOrderId();
        result = result * PRIME + ($parentOrderId == null ? 43 : $parentOrderId.hashCode());
        final Object $imgUrl = this.getImgUrl();
        result = result * PRIME + ($imgUrl == null ? 43 : $imgUrl.hashCode());
        final Object $orderDetailList = this.getOrderDetailList();
        result = result * PRIME + ($orderDetailList == null ? 43 : $orderDetailList.hashCode());
        final Object $wareHouseId = this.getWareHouseId();
        result = result * PRIME + ($wareHouseId == null ? 43 : $wareHouseId.hashCode());
        return result;
    }
}
