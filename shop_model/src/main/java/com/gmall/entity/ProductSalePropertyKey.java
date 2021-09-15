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
 * spu销售属性
 * </p>
 *
 * @author zhangqiang
 * @since 2021-03-23
 */
@Accessors(chain = true)
@TableName("product_sale_property_key")
@ApiModel(value="ProductSalePropertyKey对象", description="spu销售属性")
public class ProductSalePropertyKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号(业务中无关联)")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "商品id")
    private Long productId;

    @ApiModelProperty(value = "销售属性id")
    private Long salePropertyKeyId;

    @ApiModelProperty(value = "销售属性名称(冗余)")
    private String salePropertyKeyName;

    // 销售属性对象集合
    @TableField(exist = false)
    private List<ProductSalePropertyValue> salePropertyValueList;

    public ProductSalePropertyKey() {
    }


    public Long getId() {
        return this.id;
    }

    public Long getProductId() {
        return this.productId;
    }

    public Long getSalePropertyKeyId() {
        return this.salePropertyKeyId;
    }

    public String getSalePropertyKeyName() {
        return this.salePropertyKeyName;
    }

    public List<ProductSalePropertyValue> getSalePropertyValueList() {
        return this.salePropertyValueList;
    }

    public ProductSalePropertyKey setId(Long id) {
        this.id = id;
        return this;
    }

    public ProductSalePropertyKey setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public ProductSalePropertyKey setSalePropertyKeyId(Long salePropertyKeyId) {
        this.salePropertyKeyId = salePropertyKeyId;
        return this;
    }

    public ProductSalePropertyKey setSalePropertyKeyName(String salePropertyKeyName) {
        this.salePropertyKeyName = salePropertyKeyName;
        return this;
    }

    public ProductSalePropertyKey setSalePropertyValueList(List<ProductSalePropertyValue> salePropertyValueList) {
        this.salePropertyValueList = salePropertyValueList;
        return this;
    }

    public String toString() {
        return "ProductSalePropertyKey(id=" + this.getId() + ", productId=" + this.getProductId() + ", salePropertyKeyId=" + this.getSalePropertyKeyId() + ", salePropertyKeyName=" + this.getSalePropertyKeyName() + ", salePropertyValueList=" + this.getSalePropertyValueList() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ProductSalePropertyKey)) return false;
        final ProductSalePropertyKey other = (ProductSalePropertyKey) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$productId = this.getProductId();
        final Object other$productId = other.getProductId();
        if (this$productId == null ? other$productId != null : !this$productId.equals(other$productId)) return false;
        final Object this$salePropertyKeyId = this.getSalePropertyKeyId();
        final Object other$salePropertyKeyId = other.getSalePropertyKeyId();
        if (this$salePropertyKeyId == null ? other$salePropertyKeyId != null : !this$salePropertyKeyId.equals(other$salePropertyKeyId))
            return false;
        final Object this$salePropertyKeyName = this.getSalePropertyKeyName();
        final Object other$salePropertyKeyName = other.getSalePropertyKeyName();
        if (this$salePropertyKeyName == null ? other$salePropertyKeyName != null : !this$salePropertyKeyName.equals(other$salePropertyKeyName))
            return false;
        final Object this$salePropertyValueList = this.getSalePropertyValueList();
        final Object other$salePropertyValueList = other.getSalePropertyValueList();
        if (this$salePropertyValueList == null ? other$salePropertyValueList != null : !this$salePropertyValueList.equals(other$salePropertyValueList))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ProductSalePropertyKey;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $productId = this.getProductId();
        result = result * PRIME + ($productId == null ? 43 : $productId.hashCode());
        final Object $salePropertyKeyId = this.getSalePropertyKeyId();
        result = result * PRIME + ($salePropertyKeyId == null ? 43 : $salePropertyKeyId.hashCode());
        final Object $salePropertyKeyName = this.getSalePropertyKeyName();
        result = result * PRIME + ($salePropertyKeyName == null ? 43 : $salePropertyKeyName.hashCode());
        final Object $salePropertyValueList = this.getSalePropertyValueList();
        result = result * PRIME + ($salePropertyValueList == null ? 43 : $salePropertyValueList.hashCode());
        return result;
    }
}
