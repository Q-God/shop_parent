package com.gmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 库存单元图片表
 * </p>
 *
 * @author zhangqiang
 * @since 2021-03-24
 */
@Accessors(chain = true)
@TableName("sku_image")
@ApiModel(value="SkuImage对象", description="库存单元图片表")
public class SkuImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "sku的id")
    private Long skuId;

    @ApiModelProperty(value = "图片名称（冗余）")
    private String imageName;

    @ApiModelProperty(value = "图片路径(冗余)")
    private String imageUrl;

    @ApiModelProperty(value = "商品图片id")
    private Long productImageId;

    @ApiModelProperty(value = "是否默认")
    private String isDefault;

    public SkuImage() {
    }


    public Long getId() {
        return this.id;
    }

    public Long getSkuId() {
        return this.skuId;
    }

    public String getImageName() {
        return this.imageName;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Long getProductImageId() {
        return this.productImageId;
    }

    public String getIsDefault() {
        return this.isDefault;
    }

    public SkuImage setId(Long id) {
        this.id = id;
        return this;
    }

    public SkuImage setSkuId(Long skuId) {
        this.skuId = skuId;
        return this;
    }

    public SkuImage setImageName(String imageName) {
        this.imageName = imageName;
        return this;
    }

    public SkuImage setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public SkuImage setProductImageId(Long productImageId) {
        this.productImageId = productImageId;
        return this;
    }

    public SkuImage setIsDefault(String isDefault) {
        this.isDefault = isDefault;
        return this;
    }

    public String toString() {
        return "SkuImage(id=" + this.getId() + ", skuId=" + this.getSkuId() + ", imageName=" + this.getImageName() + ", imageUrl=" + this.getImageUrl() + ", productImageId=" + this.getProductImageId() + ", isDefault=" + this.getIsDefault() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SkuImage)) return false;
        final SkuImage other = (SkuImage) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$skuId = this.getSkuId();
        final Object other$skuId = other.getSkuId();
        if (this$skuId == null ? other$skuId != null : !this$skuId.equals(other$skuId)) return false;
        final Object this$imageName = this.getImageName();
        final Object other$imageName = other.getImageName();
        if (this$imageName == null ? other$imageName != null : !this$imageName.equals(other$imageName)) return false;
        final Object this$imageUrl = this.getImageUrl();
        final Object other$imageUrl = other.getImageUrl();
        if (this$imageUrl == null ? other$imageUrl != null : !this$imageUrl.equals(other$imageUrl)) return false;
        final Object this$productImageId = this.getProductImageId();
        final Object other$productImageId = other.getProductImageId();
        if (this$productImageId == null ? other$productImageId != null : !this$productImageId.equals(other$productImageId))
            return false;
        final Object this$isDefault = this.getIsDefault();
        final Object other$isDefault = other.getIsDefault();
        if (this$isDefault == null ? other$isDefault != null : !this$isDefault.equals(other$isDefault)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SkuImage;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $skuId = this.getSkuId();
        result = result * PRIME + ($skuId == null ? 43 : $skuId.hashCode());
        final Object $imageName = this.getImageName();
        result = result * PRIME + ($imageName == null ? 43 : $imageName.hashCode());
        final Object $imageUrl = this.getImageUrl();
        result = result * PRIME + ($imageUrl == null ? 43 : $imageUrl.hashCode());
        final Object $productImageId = this.getProductImageId();
        result = result * PRIME + ($productImageId == null ? 43 : $productImageId.hashCode());
        final Object $isDefault = this.getIsDefault();
        result = result * PRIME + ($isDefault == null ? 43 : $isDefault.hashCode());
        return result;
    }
}
