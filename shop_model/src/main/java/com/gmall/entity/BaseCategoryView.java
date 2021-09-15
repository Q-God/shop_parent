package com.gmall.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * VIEW
 * </p>
 *
 * @author zhangqiang
 * @since 2021-03-27
 */
@Accessors(chain = true)
@TableName("base_category_view")
@ApiModel(value="BaseCategoryView对象", description="VIEW")
public class BaseCategoryView implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    private Long id;

    @ApiModelProperty(value = "编号")
    private Long category1Id;

    @ApiModelProperty(value = "分类名称")
    private String category1Name;

    @ApiModelProperty(value = "编号")
    private Long category2Id;

    @ApiModelProperty(value = "二级分类名称")
    private String category2Name;

    @ApiModelProperty(value = "编号")
    private Long category3Id;

    @ApiModelProperty(value = "三级分类名称")
    private String category3Name;

    public BaseCategoryView() {
    }


    public Long getId() {
        return this.id;
    }

    public Long getCategory1Id() {
        return this.category1Id;
    }

    public String getCategory1Name() {
        return this.category1Name;
    }

    public Long getCategory2Id() {
        return this.category2Id;
    }

    public String getCategory2Name() {
        return this.category2Name;
    }

    public Long getCategory3Id() {
        return this.category3Id;
    }

    public String getCategory3Name() {
        return this.category3Name;
    }

    public BaseCategoryView setId(Long id) {
        this.id = id;
        return this;
    }

    public BaseCategoryView setCategory1Id(Long category1Id) {
        this.category1Id = category1Id;
        return this;
    }

    public BaseCategoryView setCategory1Name(String category1Name) {
        this.category1Name = category1Name;
        return this;
    }

    public BaseCategoryView setCategory2Id(Long category2Id) {
        this.category2Id = category2Id;
        return this;
    }

    public BaseCategoryView setCategory2Name(String category2Name) {
        this.category2Name = category2Name;
        return this;
    }

    public BaseCategoryView setCategory3Id(Long category3Id) {
        this.category3Id = category3Id;
        return this;
    }

    public BaseCategoryView setCategory3Name(String category3Name) {
        this.category3Name = category3Name;
        return this;
    }

    public String toString() {
        return "BaseCategoryView(id=" + this.getId() + ", category1Id=" + this.getCategory1Id() + ", category1Name=" + this.getCategory1Name() + ", category2Id=" + this.getCategory2Id() + ", category2Name=" + this.getCategory2Name() + ", category3Id=" + this.getCategory3Id() + ", category3Name=" + this.getCategory3Name() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof BaseCategoryView)) return false;
        final BaseCategoryView other = (BaseCategoryView) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$category1Id = this.getCategory1Id();
        final Object other$category1Id = other.getCategory1Id();
        if (this$category1Id == null ? other$category1Id != null : !this$category1Id.equals(other$category1Id))
            return false;
        final Object this$category1Name = this.getCategory1Name();
        final Object other$category1Name = other.getCategory1Name();
        if (this$category1Name == null ? other$category1Name != null : !this$category1Name.equals(other$category1Name))
            return false;
        final Object this$category2Id = this.getCategory2Id();
        final Object other$category2Id = other.getCategory2Id();
        if (this$category2Id == null ? other$category2Id != null : !this$category2Id.equals(other$category2Id))
            return false;
        final Object this$category2Name = this.getCategory2Name();
        final Object other$category2Name = other.getCategory2Name();
        if (this$category2Name == null ? other$category2Name != null : !this$category2Name.equals(other$category2Name))
            return false;
        final Object this$category3Id = this.getCategory3Id();
        final Object other$category3Id = other.getCategory3Id();
        if (this$category3Id == null ? other$category3Id != null : !this$category3Id.equals(other$category3Id))
            return false;
        final Object this$category3Name = this.getCategory3Name();
        final Object other$category3Name = other.getCategory3Name();
        if (this$category3Name == null ? other$category3Name != null : !this$category3Name.equals(other$category3Name))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BaseCategoryView;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $category1Id = this.getCategory1Id();
        result = result * PRIME + ($category1Id == null ? 43 : $category1Id.hashCode());
        final Object $category1Name = this.getCategory1Name();
        result = result * PRIME + ($category1Name == null ? 43 : $category1Name.hashCode());
        final Object $category2Id = this.getCategory2Id();
        result = result * PRIME + ($category2Id == null ? 43 : $category2Id.hashCode());
        final Object $category2Name = this.getCategory2Name();
        result = result * PRIME + ($category2Name == null ? 43 : $category2Name.hashCode());
        final Object $category3Id = this.getCategory3Id();
        result = result * PRIME + ($category3Id == null ? 43 : $category3Id.hashCode());
        final Object $category3Name = this.getCategory3Name();
        result = result * PRIME + ($category3Name == null ? 43 : $category3Name.hashCode());
        return result;
    }
}
