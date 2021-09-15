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
 * 商品图片表
 * </p>
 *
 * @author zhangqiang
 * @since 2021-03-23
 */
@Accessors(chain = true)
@TableName("product_image")
@ApiModel(value="ProductImage对象", description="商品图片表")
public class ProductImage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "商品id")
    private Long productId;

    @ApiModelProperty(value = "图片名称")
    private String imageName;

    @ApiModelProperty(value = "图片路径")
    private String imageUrl;

    public ProductImage() {
    }


    public Long getId() {
        return this.id;
    }

    public Long getProductId() {
        return this.productId;
    }

    public String getImageName() {
        return this.imageName;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public ProductImage setId(Long id) {
        this.id = id;
        return this;
    }

    public ProductImage setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public ProductImage setImageName(String imageName) {
        this.imageName = imageName;
        return this;
    }

    public ProductImage setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String toString() {
        return "ProductImage(id=" + this.getId() + ", productId=" + this.getProductId() + ", imageName=" + this.getImageName() + ", imageUrl=" + this.getImageUrl() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ProductImage)) return false;
        final ProductImage other = (ProductImage) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$productId = this.getProductId();
        final Object other$productId = other.getProductId();
        if (this$productId == null ? other$productId != null : !this$productId.equals(other$productId)) return false;
        final Object this$imageName = this.getImageName();
        final Object other$imageName = other.getImageName();
        if (this$imageName == null ? other$imageName != null : !this$imageName.equals(other$imageName)) return false;
        final Object this$imageUrl = this.getImageUrl();
        final Object other$imageUrl = other.getImageUrl();
        if (this$imageUrl == null ? other$imageUrl != null : !this$imageUrl.equals(other$imageUrl)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ProductImage;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $productId = this.getProductId();
        result = result * PRIME + ($productId == null ? 43 : $productId.hashCode());
        final Object $imageName = this.getImageName();
        result = result * PRIME + ($imageName == null ? 43 : $imageName.hashCode());
        final Object $imageUrl = this.getImageUrl();
        result = result * PRIME + ($imageUrl == null ? 43 : $imageUrl.hashCode());
        return result;
    }
}
