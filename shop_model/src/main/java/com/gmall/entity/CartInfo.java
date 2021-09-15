package com.gmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 购物车表 用户登录系统时更新冗余
 * </p>
 *
 * @author 尚硅谷张强
 * @since 2021-07-29
 */
@Accessors(chain = true)
@TableName("cart_info")
public class CartInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private String userId;

    /**
     * skuid
     */
    private Long skuId;

    /**
     * 放入购物车时价格
     */
    private BigDecimal cartPrice;

    /**
     * 数量
     */
    private Integer skuNum;

    /**
     * 图片文件
     */
    private String imgUrl;

    /**
     * sku名称 (冗余)
     */
    private String skuName;

    private Integer isChecked;

    // 实时价格
    @TableField(exist = false)
    BigDecimal realTimePrice;

    public CartInfo() {
    }


    public Long getId() {
        return this.id;
    }

    public String getUserId() {
        return this.userId;
    }

    public Long getSkuId() {
        return this.skuId;
    }

    public BigDecimal getCartPrice() {
        return this.cartPrice;
    }

    public Integer getSkuNum() {
        return this.skuNum;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public String getSkuName() {
        return this.skuName;
    }

    public Integer getIsChecked() {
        return this.isChecked;
    }

    public BigDecimal getRealTimePrice() {
        return this.realTimePrice;
    }

    public CartInfo setId(Long id) {
        this.id = id;
        return this;
    }

    public CartInfo setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public CartInfo setSkuId(Long skuId) {
        this.skuId = skuId;
        return this;
    }

    public CartInfo setCartPrice(BigDecimal cartPrice) {
        this.cartPrice = cartPrice;
        return this;
    }

    public CartInfo setSkuNum(Integer skuNum) {
        this.skuNum = skuNum;
        return this;
    }

    public CartInfo setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public CartInfo setSkuName(String skuName) {
        this.skuName = skuName;
        return this;
    }

    public CartInfo setIsChecked(Integer isChecked) {
        this.isChecked = isChecked;
        return this;
    }

    public CartInfo setRealTimePrice(BigDecimal realTimePrice) {
        this.realTimePrice = realTimePrice;
        return this;
    }

    public String toString() {
        return "CartInfo(id=" + this.getId() + ", userId=" + this.getUserId() + ", skuId=" + this.getSkuId() + ", cartPrice=" + this.getCartPrice() + ", skuNum=" + this.getSkuNum() + ", imgUrl=" + this.getImgUrl() + ", skuName=" + this.getSkuName() + ", isChecked=" + this.getIsChecked() + ", realTimePrice=" + this.getRealTimePrice() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CartInfo)) return false;
        final CartInfo other = (CartInfo) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        final Object this$skuId = this.getSkuId();
        final Object other$skuId = other.getSkuId();
        if (this$skuId == null ? other$skuId != null : !this$skuId.equals(other$skuId)) return false;
        final Object this$cartPrice = this.getCartPrice();
        final Object other$cartPrice = other.getCartPrice();
        if (this$cartPrice == null ? other$cartPrice != null : !this$cartPrice.equals(other$cartPrice)) return false;
        final Object this$skuNum = this.getSkuNum();
        final Object other$skuNum = other.getSkuNum();
        if (this$skuNum == null ? other$skuNum != null : !this$skuNum.equals(other$skuNum)) return false;
        final Object this$imgUrl = this.getImgUrl();
        final Object other$imgUrl = other.getImgUrl();
        if (this$imgUrl == null ? other$imgUrl != null : !this$imgUrl.equals(other$imgUrl)) return false;
        final Object this$skuName = this.getSkuName();
        final Object other$skuName = other.getSkuName();
        if (this$skuName == null ? other$skuName != null : !this$skuName.equals(other$skuName)) return false;
        final Object this$isChecked = this.getIsChecked();
        final Object other$isChecked = other.getIsChecked();
        if (this$isChecked == null ? other$isChecked != null : !this$isChecked.equals(other$isChecked)) return false;
        final Object this$realTimePrice = this.getRealTimePrice();
        final Object other$realTimePrice = other.getRealTimePrice();
        if (this$realTimePrice == null ? other$realTimePrice != null : !this$realTimePrice.equals(other$realTimePrice))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CartInfo;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $skuId = this.getSkuId();
        result = result * PRIME + ($skuId == null ? 43 : $skuId.hashCode());
        final Object $cartPrice = this.getCartPrice();
        result = result * PRIME + ($cartPrice == null ? 43 : $cartPrice.hashCode());
        final Object $skuNum = this.getSkuNum();
        result = result * PRIME + ($skuNum == null ? 43 : $skuNum.hashCode());
        final Object $imgUrl = this.getImgUrl();
        result = result * PRIME + ($imgUrl == null ? 43 : $imgUrl.hashCode());
        final Object $skuName = this.getSkuName();
        result = result * PRIME + ($skuName == null ? 43 : $skuName.hashCode());
        final Object $isChecked = this.getIsChecked();
        result = result * PRIME + ($isChecked == null ? 43 : $isChecked.hashCode());
        final Object $realTimePrice = this.getRealTimePrice();
        result = result * PRIME + ($realTimePrice == null ? 43 : $realTimePrice.hashCode());
        return result;
    }
}
