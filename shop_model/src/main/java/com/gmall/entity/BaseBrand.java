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
 * 品牌表
 * </p>
 *
 * @author zhangqiang
 * @since 2021-03-21
 */
@Accessors(chain = true)
@TableName("base_brand")
@ApiModel(value="BaseBrand对象", description="品牌表")
public class BaseBrand implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    @ApiModelProperty(value = "品牌logo的图片路径")
    private String brandLogoUrl;

    public BaseBrand() {
    }


    public Long getId() {
        return this.id;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public String getBrandLogoUrl() {
        return this.brandLogoUrl;
    }

    public BaseBrand setId(Long id) {
        this.id = id;
        return this;
    }

    public BaseBrand setBrandName(String brandName) {
        this.brandName = brandName;
        return this;
    }

    public BaseBrand setBrandLogoUrl(String brandLogoUrl) {
        this.brandLogoUrl = brandLogoUrl;
        return this;
    }

    public String toString() {
        return "BaseBrand(id=" + this.getId() + ", brandName=" + this.getBrandName() + ", brandLogoUrl=" + this.getBrandLogoUrl() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof BaseBrand)) return false;
        final BaseBrand other = (BaseBrand) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$brandName = this.getBrandName();
        final Object other$brandName = other.getBrandName();
        if (this$brandName == null ? other$brandName != null : !this$brandName.equals(other$brandName)) return false;
        final Object this$brandLogoUrl = this.getBrandLogoUrl();
        final Object other$brandLogoUrl = other.getBrandLogoUrl();
        if (this$brandLogoUrl == null ? other$brandLogoUrl != null : !this$brandLogoUrl.equals(other$brandLogoUrl))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof BaseBrand;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $brandName = this.getBrandName();
        result = result * PRIME + ($brandName == null ? 43 : $brandName.hashCode());
        final Object $brandLogoUrl = this.getBrandLogoUrl();
        result = result * PRIME + ($brandLogoUrl == null ? 43 : $brandLogoUrl.hashCode());
        return result;
    }
}
