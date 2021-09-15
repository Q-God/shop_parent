package com.gmall.search;

// 封装查询条件
public class SearchParam {
    //分类id
    private Long category1Id;;
    private Long category2Id;
    private Long category3Id;
    //品牌 brandName=
    private String brandName;
    //检索的关键字
    private String keyword;

    // 排序规则 1:综合排序/热点  2:价格
    private String order = "";

    //平台属性 页面提交的数组
    private String[] props;
    //分页信息
    private Integer pageNo = 1;
    private Integer pageSize = 3;

    public SearchParam() {
    }


    public Long getCategory1Id() {
        return this.category1Id;
    }

    public Long getCategory2Id() {
        return this.category2Id;
    }

    public Long getCategory3Id() {
        return this.category3Id;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public String getKeyword() {
        return this.keyword;
    }

    public String getOrder() {
        return this.order;
    }

    public String[] getProps() {
        return this.props;
    }

    public Integer getPageNo() {
        return this.pageNo;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setCategory1Id(Long category1Id) {
        this.category1Id = category1Id;
    }

    public void setCategory2Id(Long category2Id) {
        this.category2Id = category2Id;
    }

    public void setCategory3Id(Long category3Id) {
        this.category3Id = category3Id;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setProps(String[] props) {
        this.props = props;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SearchParam)) return false;
        final SearchParam other = (SearchParam) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$category1Id = this.getCategory1Id();
        final Object other$category1Id = other.getCategory1Id();
        if (this$category1Id == null ? other$category1Id != null : !this$category1Id.equals(other$category1Id))
            return false;
        final Object this$category2Id = this.getCategory2Id();
        final Object other$category2Id = other.getCategory2Id();
        if (this$category2Id == null ? other$category2Id != null : !this$category2Id.equals(other$category2Id))
            return false;
        final Object this$category3Id = this.getCategory3Id();
        final Object other$category3Id = other.getCategory3Id();
        if (this$category3Id == null ? other$category3Id != null : !this$category3Id.equals(other$category3Id))
            return false;
        final Object this$brandName = this.getBrandName();
        final Object other$brandName = other.getBrandName();
        if (this$brandName == null ? other$brandName != null : !this$brandName.equals(other$brandName)) return false;
        final Object this$keyword = this.getKeyword();
        final Object other$keyword = other.getKeyword();
        if (this$keyword == null ? other$keyword != null : !this$keyword.equals(other$keyword)) return false;
        final Object this$order = this.getOrder();
        final Object other$order = other.getOrder();
        if (this$order == null ? other$order != null : !this$order.equals(other$order)) return false;
        if (!java.util.Arrays.deepEquals(this.getProps(), other.getProps())) return false;
        final Object this$pageNo = this.getPageNo();
        final Object other$pageNo = other.getPageNo();
        if (this$pageNo == null ? other$pageNo != null : !this$pageNo.equals(other$pageNo)) return false;
        final Object this$pageSize = this.getPageSize();
        final Object other$pageSize = other.getPageSize();
        if (this$pageSize == null ? other$pageSize != null : !this$pageSize.equals(other$pageSize)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SearchParam;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $category1Id = this.getCategory1Id();
        result = result * PRIME + ($category1Id == null ? 43 : $category1Id.hashCode());
        final Object $category2Id = this.getCategory2Id();
        result = result * PRIME + ($category2Id == null ? 43 : $category2Id.hashCode());
        final Object $category3Id = this.getCategory3Id();
        result = result * PRIME + ($category3Id == null ? 43 : $category3Id.hashCode());
        final Object $brandName = this.getBrandName();
        result = result * PRIME + ($brandName == null ? 43 : $brandName.hashCode());
        final Object $keyword = this.getKeyword();
        result = result * PRIME + ($keyword == null ? 43 : $keyword.hashCode());
        final Object $order = this.getOrder();
        result = result * PRIME + ($order == null ? 43 : $order.hashCode());
        result = result * PRIME + java.util.Arrays.deepHashCode(this.getProps());
        final Object $pageNo = this.getPageNo();
        result = result * PRIME + ($pageNo == null ? 43 : $pageNo.hashCode());
        final Object $pageSize = this.getPageSize();
        result = result * PRIME + ($pageSize == null ? 43 : $pageSize.hashCode());
        return result;
    }

    public String toString() {
        return "SearchParam(category1Id=" + this.getCategory1Id() + ", category2Id=" + this.getCategory2Id() + ", category3Id=" + this.getCategory3Id() + ", brandName=" + this.getBrandName() + ", keyword=" + this.getKeyword() + ", order=" + this.getOrder() + ", props=" + java.util.Arrays.deepToString(this.getProps()) + ", pageNo=" + this.getPageNo() + ", pageSize=" + this.getPageSize() + ")";
    }
}
