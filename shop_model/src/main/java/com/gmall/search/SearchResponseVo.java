package com.gmall.search;

import com.gmall.enums.SearchBrandVoSingleton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// 总的数据
public class SearchResponseVo implements Serializable {

    //品牌 此时vo对象中的id字段保留（不用写） name就是“品牌” value: [{id:100,name:华为,logo:xxx},{id:101,name:小米,log:yyy}]
    private List<SearchBrandVo> brandVoList;
    //所有商品的顶头显示的筛选属性
    private List<SearchPlatformPropertyVo> platformPropertyList = new ArrayList<>();

    //检索出来的商品信息
    private List<Product> productList = new ArrayList<>();

    private Long total;//总记录数
    private Integer pageSize;//每页显示的内容
    private Integer pageNo;//当前页面
    private Long totalPages;

    public SearchResponseVo() {
    }

    public static SearchBrandVo getInstance() {
        return SearchBrandVoSingleton.INSTANCE.getInstance();
    }

    public List<SearchBrandVo> getBrandVoList() {
        return this.brandVoList;
    }

    public List<SearchPlatformPropertyVo> getPlatformPropertyList() {
        return this.platformPropertyList;
    }

    public List<Product> getProductList() {
        return this.productList;
    }

    public Long getTotal() {
        return this.total;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public Integer getPageNo() {
        return this.pageNo;
    }

    public Long getTotalPages() {
        return this.totalPages;
    }

    public void setBrandVoList(List<SearchBrandVo> brandVoList) {
        this.brandVoList = brandVoList;
    }

    public void setPlatformPropertyList(List<SearchPlatformPropertyVo> platformPropertyList) {
        this.platformPropertyList = platformPropertyList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SearchResponseVo)) return false;
        final SearchResponseVo other = (SearchResponseVo) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$brandVoList = this.getBrandVoList();
        final Object other$brandVoList = other.getBrandVoList();
        if (this$brandVoList == null ? other$brandVoList != null : !this$brandVoList.equals(other$brandVoList))
            return false;
        final Object this$platformPropertyList = this.getPlatformPropertyList();
        final Object other$platformPropertyList = other.getPlatformPropertyList();
        if (this$platformPropertyList == null ? other$platformPropertyList != null : !this$platformPropertyList.equals(other$platformPropertyList))
            return false;
        final Object this$productList = this.getProductList();
        final Object other$productList = other.getProductList();
        if (this$productList == null ? other$productList != null : !this$productList.equals(other$productList))
            return false;
        final Object this$total = this.getTotal();
        final Object other$total = other.getTotal();
        if (this$total == null ? other$total != null : !this$total.equals(other$total)) return false;
        final Object this$pageSize = this.getPageSize();
        final Object other$pageSize = other.getPageSize();
        if (this$pageSize == null ? other$pageSize != null : !this$pageSize.equals(other$pageSize)) return false;
        final Object this$pageNo = this.getPageNo();
        final Object other$pageNo = other.getPageNo();
        if (this$pageNo == null ? other$pageNo != null : !this$pageNo.equals(other$pageNo)) return false;
        final Object this$totalPages = this.getTotalPages();
        final Object other$totalPages = other.getTotalPages();
        if (this$totalPages == null ? other$totalPages != null : !this$totalPages.equals(other$totalPages))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SearchResponseVo;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $brandVoList = this.getBrandVoList();
        result = result * PRIME + ($brandVoList == null ? 43 : $brandVoList.hashCode());
        final Object $platformPropertyList = this.getPlatformPropertyList();
        result = result * PRIME + ($platformPropertyList == null ? 43 : $platformPropertyList.hashCode());
        final Object $productList = this.getProductList();
        result = result * PRIME + ($productList == null ? 43 : $productList.hashCode());
        final Object $total = this.getTotal();
        result = result * PRIME + ($total == null ? 43 : $total.hashCode());
        final Object $pageSize = this.getPageSize();
        result = result * PRIME + ($pageSize == null ? 43 : $pageSize.hashCode());
        final Object $pageNo = this.getPageNo();
        result = result * PRIME + ($pageNo == null ? 43 : $pageNo.hashCode());
        final Object $totalPages = this.getTotalPages();
        result = result * PRIME + ($totalPages == null ? 43 : $totalPages.hashCode());
        return result;
    }

    public String toString() {
        return "SearchResponseVo(brandVoList=" + this.getBrandVoList() + ", platformPropertyList=" + this.getPlatformPropertyList() + ", productList=" + this.getProductList() + ", total=" + this.getTotal() + ", pageSize=" + this.getPageSize() + ", pageNo=" + this.getPageNo() + ", totalPages=" + this.getTotalPages() + ")";
    }
}
