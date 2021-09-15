package com.gmall.search;

import java.io.Serializable;

// 品牌数据
public class SearchBrandVo implements Serializable {
    //当前属性值的所有值
    private Long brandId;
    //属性名称
    private String brandName;
    //图片名称
    private String brandLogoUrl;

    public SearchBrandVo() {
    }

    public Long getBrandId() {
        return this.brandId;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public String getBrandLogoUrl() {
        return this.brandLogoUrl;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setBrandLogoUrl(String brandLogoUrl) {
        this.brandLogoUrl = brandLogoUrl;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SearchBrandVo)) return false;
        final SearchBrandVo other = (SearchBrandVo) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$brandId = this.getBrandId();
        final Object other$brandId = other.getBrandId();
        if (this$brandId == null ? other$brandId != null : !this$brandId.equals(other$brandId)) return false;
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
        return other instanceof SearchBrandVo;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $brandId = this.getBrandId();
        result = result * PRIME + ($brandId == null ? 43 : $brandId.hashCode());
        final Object $brandName = this.getBrandName();
        result = result * PRIME + ($brandName == null ? 43 : $brandName.hashCode());
        final Object $brandLogoUrl = this.getBrandLogoUrl();
        result = result * PRIME + ($brandLogoUrl == null ? 43 : $brandLogoUrl.hashCode());
        return result;
    }

    public String toString() {
        return "SearchBrandVo(brandId=" + this.getBrandId() + ", brandName=" + this.getBrandName() + ", brandLogoUrl=" + this.getBrandLogoUrl() + ")";
    }
}

