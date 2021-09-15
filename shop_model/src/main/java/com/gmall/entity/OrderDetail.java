package com.gmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 订单明细表
 * </p>
 *
 * @author 尚硅谷张强
 * @since 2021-07-30
 */
@Accessors(chain = true)
@TableName("order_detail")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单编号
     */
    private Long orderId;

    /**
     * sku_id
     */
    private Long skuId;

    /**
     * sku名称（冗余)
     */
    private String skuName;

    /**
     * 图片名称（冗余)
     */
    private String imgUrl;

    /**
     * 购买价格(下单时sku价格）
     */
    private BigDecimal orderPrice;

    /**
     * 购买个数
     */
    private String skuNum;

    public OrderDetail() {
    }


    public Long getId() {
        return this.id;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public Long getSkuId() {
        return this.skuId;
    }

    public String getSkuName() {
        return this.skuName;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public BigDecimal getOrderPrice() {
        return this.orderPrice;
    }

    public String getSkuNum() {
        return this.skuNum;
    }

    public OrderDetail setId(Long id) {
        this.id = id;
        return this;
    }

    public OrderDetail setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public OrderDetail setSkuId(Long skuId) {
        this.skuId = skuId;
        return this;
    }

    public OrderDetail setSkuName(String skuName) {
        this.skuName = skuName;
        return this;
    }

    public OrderDetail setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public OrderDetail setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
        return this;
    }

    public OrderDetail setSkuNum(String skuNum) {
        this.skuNum = skuNum;
        return this;
    }

    public String toString() {
        return "OrderDetail(id=" + this.getId() + ", orderId=" + this.getOrderId() + ", skuId=" + this.getSkuId() + ", skuName=" + this.getSkuName() + ", imgUrl=" + this.getImgUrl() + ", orderPrice=" + this.getOrderPrice() + ", skuNum=" + this.getSkuNum() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof OrderDetail)) return false;
        final OrderDetail other = (OrderDetail) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$orderId = this.getOrderId();
        final Object other$orderId = other.getOrderId();
        if (this$orderId == null ? other$orderId != null : !this$orderId.equals(other$orderId)) return false;
        final Object this$skuId = this.getSkuId();
        final Object other$skuId = other.getSkuId();
        if (this$skuId == null ? other$skuId != null : !this$skuId.equals(other$skuId)) return false;
        final Object this$skuName = this.getSkuName();
        final Object other$skuName = other.getSkuName();
        if (this$skuName == null ? other$skuName != null : !this$skuName.equals(other$skuName)) return false;
        final Object this$imgUrl = this.getImgUrl();
        final Object other$imgUrl = other.getImgUrl();
        if (this$imgUrl == null ? other$imgUrl != null : !this$imgUrl.equals(other$imgUrl)) return false;
        final Object this$orderPrice = this.getOrderPrice();
        final Object other$orderPrice = other.getOrderPrice();
        if (this$orderPrice == null ? other$orderPrice != null : !this$orderPrice.equals(other$orderPrice))
            return false;
        final Object this$skuNum = this.getSkuNum();
        final Object other$skuNum = other.getSkuNum();
        if (this$skuNum == null ? other$skuNum != null : !this$skuNum.equals(other$skuNum)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof OrderDetail;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $orderId = this.getOrderId();
        result = result * PRIME + ($orderId == null ? 43 : $orderId.hashCode());
        final Object $skuId = this.getSkuId();
        result = result * PRIME + ($skuId == null ? 43 : $skuId.hashCode());
        final Object $skuName = this.getSkuName();
        result = result * PRIME + ($skuName == null ? 43 : $skuName.hashCode());
        final Object $imgUrl = this.getImgUrl();
        result = result * PRIME + ($imgUrl == null ? 43 : $imgUrl.hashCode());
        final Object $orderPrice = this.getOrderPrice();
        result = result * PRIME + ($orderPrice == null ? 43 : $orderPrice.hashCode());
        final Object $skuNum = this.getSkuNum();
        result = result * PRIME + ($skuNum == null ? 43 : $skuNum.hashCode());
        return result;
    }
}
