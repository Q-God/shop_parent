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
 * 二级分类表
 * </p>
 *
 * @author zhangqiang
 * @since 2021-03-20
 */
@Accessors(chain = true)
@TableName("base_category2")
@ApiModel(value="BaseCategory2对象", description="二级分类表")
public class BaseCategory2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "二级分类名称")
    private String name;

    @ApiModelProperty(value = "一级分类编号")
    private Long category1Id;

    public BaseCategory2() {
    }


    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Long getCategory1Id() {
        return this.category1Id;
    }

    public BaseCategory2 setId(Long id) {
        this.id = id;
        return this;
    }

    public BaseCategory2 setName(String name) {
        this.name = name;
        return this;
    }

    public BaseCategory2 setCategory1Id(Long category1Id) {
        this.category1Id = category1Id;
        return this;
    }

    public String toString() {
        return "BaseCategory2(id=" + this.getId() + ", name=" + this.getName() + ", category1Id=" + this.getCategory1Id() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof BaseCategory2)) return false;
        final BaseCategory2 other = (BaseCategory2) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$category1Id = this.getCategory1Id();
        final Object other$category1Id = other.getCategory1Id();
        if (this$category1Id == null ? other$category1Id != null : !this$category1Id.equals(other$category1Id))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BaseCategory2;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $category1Id = this.getCategory1Id();
        result = result * PRIME + ($category1Id == null ? 43 : $category1Id.hashCode());
        return result;
    }
}
