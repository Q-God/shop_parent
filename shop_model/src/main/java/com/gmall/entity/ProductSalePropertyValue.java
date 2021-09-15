package com.gmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * spu销售属性值
 * </p>
 *
 * @author zhangqiang
 * @since 2021-03-23
 */
@Accessors(chain = true)
@TableName("product_sale_property_value")
@ApiModel(value="ProductSalePropertyValue对象", description="spu销售属性值")
public class ProductSalePropertyValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "销售属性值编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "商品id")
    private Long productId;

    @ApiModelProperty(value = "销售属性id")
    private Long salePropertyKeyId;

    @ApiModelProperty(value = "销售属性名称(冗余)")
    private String salePropertyKeyName;

    @ApiModelProperty(value = "销售属性值名称")
    private String salePropertyValueName;

    //是否是默认选中状态
    @TableField(exist = false)
    String isSelected;

    public ProductSalePropertyValue() {
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

    public String getSalePropertyValueName() {
        return this.salePropertyValueName;
    }

    public String getIsSelected() {
        return this.isSelected;
    }

    public ProductSalePropertyValue setId(Long id) {
        this.id = id;
        return this;
    }

    public ProductSalePropertyValue setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public ProductSalePropertyValue setSalePropertyKeyId(Long salePropertyKeyId) {
        this.salePropertyKeyId = salePropertyKeyId;
        return this;
    }

    public ProductSalePropertyValue setSalePropertyKeyName(String salePropertyKeyName) {
        this.salePropertyKeyName = salePropertyKeyName;
        return this;
    }

    public ProductSalePropertyValue setSalePropertyValueName(String salePropertyValueName) {
        this.salePropertyValueName = salePropertyValueName;
        return this;
    }

    public ProductSalePropertyValue setIsSelected(String isSelected) {
        this.isSelected = isSelected;
        return this;
    }

    public String toString() {
        return "ProductSalePropertyValue(id=" + this.getId() + ", productId=" + this.getProductId() + ", salePropertyKeyId=" + this.getSalePropertyKeyId() + ", salePropertyKeyName=" + this.getSalePropertyKeyName() + ", salePropertyValueName=" + this.getSalePropertyValueName() + ", isSelected=" + this.getIsSelected() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ProductSalePropertyValue)) return false;
        final ProductSalePropertyValue other = (ProductSalePropertyValue) o;
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
        final Object this$salePropertyValueName = this.getSalePropertyValueName();
        final Object other$salePropertyValueName = other.getSalePropertyValueName();
        if (this$salePropertyValueName == null ? other$salePropertyValueName != null : !this$salePropertyValueName.equals(other$salePropertyValueName))
            return false;
        final Object this$isSelected = this.getIsSelected();
        final Object other$isSelected = other.getIsSelected();
        if (this$isSelected == null ? other$isSelected != null : !this$isSelected.equals(other$isSelected))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ProductSalePropertyValue;
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
        final Object $salePropertyValueName = this.getSalePropertyValueName();
        result = result * PRIME + ($salePropertyValueName == null ? 43 : $salePropertyValueName.hashCode());
        final Object $isSelected = this.getIsSelected();
        result = result * PRIME + ($isSelected == null ? 43 : $isSelected.hashCode());
        return result;
    }
}
