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
 * sku销售属性值
 * </p>
 *
 * @author zhangqiang
 * @since 2021-03-24
 */
@Accessors(chain = true)
@TableName("sku_sale_property_value")
@ApiModel(value="SkuSalePropertyValue对象", description="sku销售属性值")
public class SkuSalePropertyValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "库存单元id")
    private Long skuId;

    @ApiModelProperty(value = "spu_id(冗余)")
    private Long productId;

    @ApiModelProperty(value = "销售属性值id")
    private Long salePropertyValueId;

    public SkuSalePropertyValue() {
    }


    public Long getId() {
        return this.id;
    }

    public Long getSkuId() {
        return this.skuId;
    }

    public Long getProductId() {
        return this.productId;
    }

    public Long getSalePropertyValueId() {
        return this.salePropertyValueId;
    }

    public SkuSalePropertyValue setId(Long id) {
        this.id = id;
        return this;
    }

    public SkuSalePropertyValue setSkuId(Long skuId) {
        this.skuId = skuId;
        return this;
    }

    public SkuSalePropertyValue setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public SkuSalePropertyValue setSalePropertyValueId(Long salePropertyValueId) {
        this.salePropertyValueId = salePropertyValueId;
        return this;
    }

    public String toString() {
        return "SkuSalePropertyValue(id=" + this.getId() + ", skuId=" + this.getSkuId() + ", productId=" + this.getProductId() + ", salePropertyValueId=" + this.getSalePropertyValueId() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SkuSalePropertyValue)) return false;
        final SkuSalePropertyValue other = (SkuSalePropertyValue) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$skuId = this.getSkuId();
        final Object other$skuId = other.getSkuId();
        if (this$skuId == null ? other$skuId != null : !this$skuId.equals(other$skuId)) return false;
        final Object this$productId = this.getProductId();
        final Object other$productId = other.getProductId();
        if (this$productId == null ? other$productId != null : !this$productId.equals(other$productId)) return false;
        final Object this$salePropertyValueId = this.getSalePropertyValueId();
        final Object other$salePropertyValueId = other.getSalePropertyValueId();
        if (this$salePropertyValueId == null ? other$salePropertyValueId != null : !this$salePropertyValueId.equals(other$salePropertyValueId))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SkuSalePropertyValue;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $skuId = this.getSkuId();
        result = result * PRIME + ($skuId == null ? 43 : $skuId.hashCode());
        final Object $productId = this.getProductId();
        result = result * PRIME + ($productId == null ? 43 : $productId.hashCode());
        final Object $salePropertyValueId = this.getSalePropertyValueId();
        result = result * PRIME + ($salePropertyValueId == null ? 43 : $salePropertyValueId.hashCode());
        return result;
    }
}
