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
 * 基本销售属性表
 * </p>
 *
 * @author zhangqiang
 * @since 2021-03-23
 */
@Accessors(chain = true)
@TableName("base_sale_property")
@ApiModel(value="BaseSaleProperty对象", description="基本销售属性表")
public class BaseSaleProperty implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "销售属性名称")
    private String name;

    public BaseSaleProperty() {
    }


    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public BaseSaleProperty setId(Long id) {
        this.id = id;
        return this;
    }

    public BaseSaleProperty setName(String name) {
        this.name = name;
        return this;
    }

    public String toString() {
        return "BaseSaleProperty(id=" + this.getId() + ", name=" + this.getName() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof BaseSaleProperty)) return false;
        final BaseSaleProperty other = (BaseSaleProperty) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BaseSaleProperty;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        return result;
    }
}
