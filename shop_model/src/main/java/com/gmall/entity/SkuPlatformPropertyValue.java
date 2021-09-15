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
 * sku平台属性值关联表
 * </p>
 *
 * @author zhangqiang
 * @since 2021-03-24
 */
@Accessors(chain = true)
@TableName("sku_platform_property_value")
@ApiModel(value="SkuPlatformPropertyValue对象", description="sku平台属性值关联表")
public class SkuPlatformPropertyValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "属性id（冗余)")
    private Long propertyKeyId;

    @ApiModelProperty(value = "属性值id")
    private Long propertyValueId;

    @ApiModelProperty(value = "skuid")
    private Long skuId;

    public SkuPlatformPropertyValue() {
    }


    public Long getId() {
        return this.id;
    }

    public Long getPropertyKeyId() {
        return this.propertyKeyId;
    }

    public Long getPropertyValueId() {
        return this.propertyValueId;
    }

    public Long getSkuId() {
        return this.skuId;
    }

    public SkuPlatformPropertyValue setId(Long id) {
        this.id = id;
        return this;
    }

    public SkuPlatformPropertyValue setPropertyKeyId(Long propertyKeyId) {
        this.propertyKeyId = propertyKeyId;
        return this;
    }

    public SkuPlatformPropertyValue setPropertyValueId(Long propertyValueId) {
        this.propertyValueId = propertyValueId;
        return this;
    }

    public SkuPlatformPropertyValue setSkuId(Long skuId) {
        this.skuId = skuId;
        return this;
    }

    public String toString() {
        return "SkuPlatformPropertyValue(id=" + this.getId() + ", propertyKeyId=" + this.getPropertyKeyId() + ", propertyValueId=" + this.getPropertyValueId() + ", skuId=" + this.getSkuId() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SkuPlatformPropertyValue)) return false;
        final SkuPlatformPropertyValue other = (SkuPlatformPropertyValue) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$propertyKeyId = this.getPropertyKeyId();
        final Object other$propertyKeyId = other.getPropertyKeyId();
        if (this$propertyKeyId == null ? other$propertyKeyId != null : !this$propertyKeyId.equals(other$propertyKeyId))
            return false;
        final Object this$propertyValueId = this.getPropertyValueId();
        final Object other$propertyValueId = other.getPropertyValueId();
        if (this$propertyValueId == null ? other$propertyValueId != null : !this$propertyValueId.equals(other$propertyValueId))
            return false;
        final Object this$skuId = this.getSkuId();
        final Object other$skuId = other.getSkuId();
        if (this$skuId == null ? other$skuId != null : !this$skuId.equals(other$skuId)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SkuPlatformPropertyValue;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $propertyKeyId = this.getPropertyKeyId();
        result = result * PRIME + ($propertyKeyId == null ? 43 : $propertyKeyId.hashCode());
        final Object $propertyValueId = this.getPropertyValueId();
        result = result * PRIME + ($propertyValueId == null ? 43 : $propertyValueId.hashCode());
        final Object $skuId = this.getSkuId();
        result = result * PRIME + ($skuId == null ? 43 : $skuId.hashCode());
        return result;
    }
}
