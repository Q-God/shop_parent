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
 * 三级分类表
 * </p>
 *
 * @author zhangqiang
 * @since 2021-03-20
 */
@Accessors(chain = true)
@TableName("base_category3")
@ApiModel(value="BaseCategory3对象", description="三级分类表")
public class BaseCategory3 implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "三级分类名称")
    private String name;

    @ApiModelProperty(value = "二级分类编号")
    private Long category2Id;

    public BaseCategory3() {
    }


    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Long getCategory2Id() {
        return this.category2Id;
    }

    public BaseCategory3 setId(Long id) {
        this.id = id;
        return this;
    }

    public BaseCategory3 setName(String name) {
        this.name = name;
        return this;
    }

    public BaseCategory3 setCategory2Id(Long category2Id) {
        this.category2Id = category2Id;
        return this;
    }

    public String toString() {
        return "BaseCategory3(id=" + this.getId() + ", name=" + this.getName() + ", category2Id=" + this.getCategory2Id() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof BaseCategory3)) return false;
        final BaseCategory3 other = (BaseCategory3) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$category2Id = this.getCategory2Id();
        final Object other$category2Id = other.getCategory2Id();
        if (this$category2Id == null ? other$category2Id != null : !this$category2Id.equals(other$category2Id))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BaseCategory3;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $category2Id = this.getCategory2Id();
        result = result * PRIME + ($category2Id == null ? 43 : $category2Id.hashCode());
        return result;
    }
}
