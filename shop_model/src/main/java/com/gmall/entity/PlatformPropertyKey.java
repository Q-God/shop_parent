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
 * 属性表
 * </p>
 *
 * @author zhangqiang
 * @since 2021-03-21
 */
@Accessors(chain = true)
@TableName("platform_property_key")
@ApiModel(value="PlatformPropertyKey对象", description="属性表")
public class PlatformPropertyKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "属性名称")
    private String propertyKey;

    @ApiModelProperty(value = "分类id")
    private Long categoryId;

    @ApiModelProperty(value = "分类层级")
    private Integer categoryLevel;

    //表示非数据库字段,但是业务需要使用!
    @TableField(exist = false)
    private List<PlatformPropertyValue> propertyValueList;

    public PlatformPropertyKey() {
    }


    public Long getId() {
        return this.id;
    }

    public String getPropertyKey() {
        return this.propertyKey;
    }

    public Long getCategoryId() {
        return this.categoryId;
    }

    public Integer getCategoryLevel() {
        return this.categoryLevel;
    }

    public List<PlatformPropertyValue> getPropertyValueList() {
        return this.propertyValueList;
    }

    public PlatformPropertyKey setId(Long id) {
        this.id = id;
        return this;
    }

    public PlatformPropertyKey setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
        return this;
    }

    public PlatformPropertyKey setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public PlatformPropertyKey setCategoryLevel(Integer categoryLevel) {
        this.categoryLevel = categoryLevel;
        return this;
    }

    public PlatformPropertyKey setPropertyValueList(List<PlatformPropertyValue> propertyValueList) {
        this.propertyValueList = propertyValueList;
        return this;
    }

    public String toString() {
        return "PlatformPropertyKey(id=" + this.getId() + ", propertyKey=" + this.getPropertyKey() + ", categoryId=" + this.getCategoryId() + ", categoryLevel=" + this.getCategoryLevel() + ", propertyValueList=" + this.getPropertyValueList() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof PlatformPropertyKey)) return false;
        final PlatformPropertyKey other = (PlatformPropertyKey) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$propertyKey = this.getPropertyKey();
        final Object other$propertyKey = other.getPropertyKey();
        if (this$propertyKey == null ? other$propertyKey != null : !this$propertyKey.equals(other$propertyKey))
            return false;
        final Object this$categoryId = this.getCategoryId();
        final Object other$categoryId = other.getCategoryId();
        if (this$categoryId == null ? other$categoryId != null : !this$categoryId.equals(other$categoryId))
            return false;
        final Object this$categoryLevel = this.getCategoryLevel();
        final Object other$categoryLevel = other.getCategoryLevel();
        if (this$categoryLevel == null ? other$categoryLevel != null : !this$categoryLevel.equals(other$categoryLevel))
            return false;
        final Object this$propertyValueList = this.getPropertyValueList();
        final Object other$propertyValueList = other.getPropertyValueList();
        if (this$propertyValueList == null ? other$propertyValueList != null : !this$propertyValueList.equals(other$propertyValueList))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof PlatformPropertyKey;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $propertyKey = this.getPropertyKey();
        result = result * PRIME + ($propertyKey == null ? 43 : $propertyKey.hashCode());
        final Object $categoryId = this.getCategoryId();
        result = result * PRIME + ($categoryId == null ? 43 : $categoryId.hashCode());
        final Object $categoryLevel = this.getCategoryLevel();
        result = result * PRIME + ($categoryLevel == null ? 43 : $categoryLevel.hashCode());
        final Object $propertyValueList = this.getPropertyValueList();
        result = result * PRIME + ($propertyValueList == null ? 43 : $propertyValueList.hashCode());
        return result;
    }
}
