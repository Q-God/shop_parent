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
 * 属性值表
 * </p>
 *
 * @author zhangqiang
 * @since 2021-03-21
 */
@Accessors(chain = true)
@TableName("platform_property_value")
@ApiModel(value="PlatformPropertyValue对象", description="属性值表")
public class PlatformPropertyValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "属性值名称")
    private String propertyValue;

    @ApiModelProperty(value = "属性id")
    private Long propertyKeyId;

    public PlatformPropertyValue() {
    }


    public Long getId() {
        return this.id;
    }

    public String getPropertyValue() {
        return this.propertyValue;
    }

    public Long getPropertyKeyId() {
        return this.propertyKeyId;
    }

    public PlatformPropertyValue setId(Long id) {
        this.id = id;
        return this;
    }

    public PlatformPropertyValue setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
        return this;
    }

    public PlatformPropertyValue setPropertyKeyId(Long propertyKeyId) {
        this.propertyKeyId = propertyKeyId;
        return this;
    }

    public String toString() {
        return "PlatformPropertyValue(id=" + this.getId() + ", propertyValue=" + this.getPropertyValue() + ", propertyKeyId=" + this.getPropertyKeyId() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof PlatformPropertyValue)) return false;
        final PlatformPropertyValue other = (PlatformPropertyValue) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$propertyValue = this.getPropertyValue();
        final Object other$propertyValue = other.getPropertyValue();
        if (this$propertyValue == null ? other$propertyValue != null : !this$propertyValue.equals(other$propertyValue))
            return false;
        final Object this$propertyKeyId = this.getPropertyKeyId();
        final Object other$propertyKeyId = other.getPropertyKeyId();
        if (this$propertyKeyId == null ? other$propertyKeyId != null : !this$propertyKeyId.equals(other$propertyKeyId))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PlatformPropertyValue;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $propertyValue = this.getPropertyValue();
        result = result * PRIME + ($propertyValue == null ? 43 : $propertyValue.hashCode());
        final Object $propertyKeyId = this.getPropertyKeyId();
        result = result * PRIME + ($propertyKeyId == null ? 43 : $propertyKeyId.hashCode());
        return result;
    }
}
