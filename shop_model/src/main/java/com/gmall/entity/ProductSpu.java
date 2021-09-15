package com.gmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author zhangqiang
 * @since 2021-03-23
 */
@Accessors(chain = true)
@TableName("product_spu")
@ApiModel(value="ProductSpu对象", description="商品表")
public class ProductSpu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "商品描述(后台简述）")
    private String description;

    @ApiModelProperty(value = "三级分类id")
    private Long category3Id;

    @ApiModelProperty(value = "品牌id")
    private Long brandId;

    // 销售属性集合
    @TableField(exist = false)
    private List<ProductSalePropertyKey> salePropertyKeyList;

    @TableField(exist = false)
    private List<ProductImage> productImageList;

    public ProductSpu() {
    }


    public Long getId() {
        return this.id;
    }

    public String getProductName() {
        return this.productName;
    }

    public String getDescription() {
        return this.description;
    }

    public Long getCategory3Id() {
        return this.category3Id;
    }

    public Long getBrandId() {
        return this.brandId;
    }

    public List<ProductSalePropertyKey> getSalePropertyKeyList() {
        return this.salePropertyKeyList;
    }

    public List<ProductImage> getProductImageList() {
        return this.productImageList;
    }

    public ProductSpu setId(Long id) {
        this.id = id;
        return this;
    }

    public ProductSpu setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public ProductSpu setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProductSpu setCategory3Id(Long category3Id) {
        this.category3Id = category3Id;
        return this;
    }

    public ProductSpu setBrandId(Long brandId) {
        this.brandId = brandId;
        return this;
    }

    public ProductSpu setSalePropertyKeyList(List<ProductSalePropertyKey> salePropertyKeyList) {
        this.salePropertyKeyList = salePropertyKeyList;
        return this;
    }

    public ProductSpu setProductImageList(List<ProductImage> productImageList) {
        this.productImageList = productImageList;
        return this;
    }

    public String toString() {
        return "ProductSpu(id=" + this.getId() + ", productName=" + this.getProductName() + ", description=" + this.getDescription() + ", category3Id=" + this.getCategory3Id() + ", brandId=" + this.getBrandId() + ", salePropertyKeyList=" + this.getSalePropertyKeyList() + ", productImageList=" + this.getProductImageList() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ProductSpu)) return false;
        final ProductSpu other = (ProductSpu) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$productName = this.getProductName();
        final Object other$productName = other.getProductName();
        if (this$productName == null ? other$productName != null : !this$productName.equals(other$productName))
            return false;
        final Object this$description = this.getDescription();
        final Object other$description = other.getDescription();
        if (this$description == null ? other$description != null : !this$description.equals(other$description))
            return false;
        final Object this$category3Id = this.getCategory3Id();
        final Object other$category3Id = other.getCategory3Id();
        if (this$category3Id == null ? other$category3Id != null : !this$category3Id.equals(other$category3Id))
            return false;
        final Object this$brandId = this.getBrandId();
        final Object other$brandId = other.getBrandId();
        if (this$brandId == null ? other$brandId != null : !this$brandId.equals(other$brandId)) return false;
        final Object this$salePropertyKeyList = this.getSalePropertyKeyList();
        final Object other$salePropertyKeyList = other.getSalePropertyKeyList();
        if (this$salePropertyKeyList == null ? other$salePropertyKeyList != null : !this$salePropertyKeyList.equals(other$salePropertyKeyList))
            return false;
        final Object this$productImageList = this.getProductImageList();
        final Object other$productImageList = other.getProductImageList();
        if (this$productImageList == null ? other$productImageList != null : !this$productImageList.equals(other$productImageList))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ProductSpu;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $productName = this.getProductName();
        result = result * PRIME + ($productName == null ? 43 : $productName.hashCode());
        final Object $description = this.getDescription();
        result = result * PRIME + ($description == null ? 43 : $description.hashCode());
        final Object $category3Id = this.getCategory3Id();
        result = result * PRIME + ($category3Id == null ? 43 : $category3Id.hashCode());
        final Object $brandId = this.getBrandId();
        result = result * PRIME + ($brandId == null ? 43 : $brandId.hashCode());
        final Object $salePropertyKeyList = this.getSalePropertyKeyList();
        result = result * PRIME + ($salePropertyKeyList == null ? 43 : $salePropertyKeyList.hashCode());
        final Object $productImageList = this.getProductImageList();
        result = result * PRIME + ($productImageList == null ? 43 : $productImageList.hashCode());
        return result;
    }
}
