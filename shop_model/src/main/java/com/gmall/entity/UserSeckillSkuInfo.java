package com.gmall.entity;

import java.io.Serializable;

public class UserSeckillSkuInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long skuId;

	private String userId;

    public UserSeckillSkuInfo() {
    }

    public Long getSkuId() {
        return this.skuId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserSeckillSkuInfo)) return false;
        final UserSeckillSkuInfo other = (UserSeckillSkuInfo) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$skuId = this.getSkuId();
        final Object other$skuId = other.getSkuId();
        if (this$skuId == null ? other$skuId != null : !this$skuId.equals(other$skuId)) return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserSeckillSkuInfo;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $skuId = this.getSkuId();
        result = result * PRIME + ($skuId == null ? 43 : $skuId.hashCode());
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        return result;
    }

    public String toString() {
        return "UserSeckillSkuInfo(skuId=" + this.getSkuId() + ", userId=" + this.getUserId() + ")";
    }
}
