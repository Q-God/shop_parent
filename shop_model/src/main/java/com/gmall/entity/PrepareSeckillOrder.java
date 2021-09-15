package com.gmall.entity;

import java.io.Serializable;

public class PrepareSeckillOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId;

	private SeckillProduct seckillProduct;

	private Integer buyNum;

	private String prepareOrderCode;

    public PrepareSeckillOrder() {
    }

    public String getUserId() {
        return this.userId;
    }

    public SeckillProduct getSeckillProduct() {
        return this.seckillProduct;
    }

    public Integer getBuyNum() {
        return this.buyNum;
    }

    public String getPrepareOrderCode() {
        return this.prepareOrderCode;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setSeckillProduct(SeckillProduct seckillProduct) {
        this.seckillProduct = seckillProduct;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public void setPrepareOrderCode(String prepareOrderCode) {
        this.prepareOrderCode = prepareOrderCode;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof PrepareSeckillOrder)) return false;
        final PrepareSeckillOrder other = (PrepareSeckillOrder) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        final Object this$seckillProduct = this.getSeckillProduct();
        final Object other$seckillProduct = other.getSeckillProduct();
        if (this$seckillProduct == null ? other$seckillProduct != null : !this$seckillProduct.equals(other$seckillProduct))
            return false;
        final Object this$buyNum = this.getBuyNum();
        final Object other$buyNum = other.getBuyNum();
        if (this$buyNum == null ? other$buyNum != null : !this$buyNum.equals(other$buyNum)) return false;
        final Object this$prepareOrderCode = this.getPrepareOrderCode();
        final Object other$prepareOrderCode = other.getPrepareOrderCode();
        if (this$prepareOrderCode == null ? other$prepareOrderCode != null : !this$prepareOrderCode.equals(other$prepareOrderCode))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PrepareSeckillOrder;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $seckillProduct = this.getSeckillProduct();
        result = result * PRIME + ($seckillProduct == null ? 43 : $seckillProduct.hashCode());
        final Object $buyNum = this.getBuyNum();
        result = result * PRIME + ($buyNum == null ? 43 : $buyNum.hashCode());
        final Object $prepareOrderCode = this.getPrepareOrderCode();
        result = result * PRIME + ($prepareOrderCode == null ? 43 : $prepareOrderCode.hashCode());
        return result;
    }

    public String toString() {
        return "PrepareSeckillOrder(userId=" + this.getUserId() + ", seckillProduct=" + this.getSeckillProduct() + ", buyNum=" + this.getBuyNum() + ", prepareOrderCode=" + this.getPrepareOrderCode() + ")";
    }
}
