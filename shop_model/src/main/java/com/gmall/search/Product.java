package com.gmall.search;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;
import java.util.List;

/**
 * 这个类就是我们要存储的商品数据，将商品数据放入index,type
 * index,type 如何指定。通过spring-boot-starter-data-elasticsearch 需要添加注解表明
 * index=goods type=info shards = 分片 replicas =副本 | 保证es 查询效率，提高负载均衡用！
 */
@Document(indexName = "product" ,type = "info",shards = 3,replicas = 2)
public class Product {
    // 商品Id
    @Id
    private Long id;

    // 商品的默认图片
    @Field(type = FieldType.Keyword, index = false)
    private String defaultImage;

    // 商品名称
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String productName;

    // 商品价格
    @Field(type = FieldType.Double)
    private Double price;

    // 创建时间
    @Field(type = FieldType.Date)
    private Date createTime; // 新品

    // 品牌Id
    @Field(type = FieldType.Long)
    private Long brandId;

    // 品牌名称
    @Field(type = FieldType.Keyword)
    private String brandName;

    // 品牌logo
    @Field(type = FieldType.Keyword)
    private String brandLogoUrl;

    @Field(type = FieldType.Long)
    private Long category1Id;

    @Field(type = FieldType.Keyword)
    private String category1Name;

    @Field(type = FieldType.Long)
    private Long category2Id;

    @Field(type = FieldType.Keyword)
    private String category2Name;

    @Field(type = FieldType.Long)
    private Long category3Id;

    @Field(type = FieldType.Keyword)
    private String category3Name;

    //热度排名
    @Field(type = FieldType.Long)
    private Long hotScore = 0L;

    //平台属性集合对象
    //Nested支持嵌套查询
    @Field(type = FieldType.Nested)
    private List<SearchPlatformProperty> platformProperty;

    public Product() {
    }

    public Long getId() {
        return this.id;
    }

    public String getDefaultImage() {
        return this.defaultImage;
    }

    public String getProductName() {
        return this.productName;
    }

    public Double getPrice() {
        return this.price;
    }

    public Date getCreateTime() {
        return this.createTime;
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

    public Long getHotScore() {
        return this.hotScore;
    }

    public List<SearchPlatformProperty> getPlatformProperty() {
        return this.platformProperty;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDefaultImage(String defaultImage) {
        this.defaultImage = defaultImage;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public void setCategory1Id(Long category1Id) {
        this.category1Id = category1Id;
    }

    public void setCategory1Name(String category1Name) {
        this.category1Name = category1Name;
    }

    public void setCategory2Id(Long category2Id) {
        this.category2Id = category2Id;
    }

    public void setCategory2Name(String category2Name) {
        this.category2Name = category2Name;
    }

    public void setCategory3Id(Long category3Id) {
        this.category3Id = category3Id;
    }

    public void setCategory3Name(String category3Name) {
        this.category3Name = category3Name;
    }

    public void setHotScore(Long hotScore) {
        this.hotScore = hotScore;
    }

    public void setPlatformProperty(List<SearchPlatformProperty> platformProperty) {
        this.platformProperty = platformProperty;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Product)) return false;
        final Product other = (Product) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$defaultImage = this.getDefaultImage();
        final Object other$defaultImage = other.getDefaultImage();
        if (this$defaultImage == null ? other$defaultImage != null : !this$defaultImage.equals(other$defaultImage))
            return false;
        final Object this$productName = this.getProductName();
        final Object other$productName = other.getProductName();
        if (this$productName == null ? other$productName != null : !this$productName.equals(other$productName))
            return false;
        final Object this$price = this.getPrice();
        final Object other$price = other.getPrice();
        if (this$price == null ? other$price != null : !this$price.equals(other$price)) return false;
        final Object this$createTime = this.getCreateTime();
        final Object other$createTime = other.getCreateTime();
        if (this$createTime == null ? other$createTime != null : !this$createTime.equals(other$createTime))
            return false;
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
        final Object this$hotScore = this.getHotScore();
        final Object other$hotScore = other.getHotScore();
        if (this$hotScore == null ? other$hotScore != null : !this$hotScore.equals(other$hotScore)) return false;
        final Object this$platformProperty = this.getPlatformProperty();
        final Object other$platformProperty = other.getPlatformProperty();
        if (this$platformProperty == null ? other$platformProperty != null : !this$platformProperty.equals(other$platformProperty))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Product;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $defaultImage = this.getDefaultImage();
        result = result * PRIME + ($defaultImage == null ? 43 : $defaultImage.hashCode());
        final Object $productName = this.getProductName();
        result = result * PRIME + ($productName == null ? 43 : $productName.hashCode());
        final Object $price = this.getPrice();
        result = result * PRIME + ($price == null ? 43 : $price.hashCode());
        final Object $createTime = this.getCreateTime();
        result = result * PRIME + ($createTime == null ? 43 : $createTime.hashCode());
        final Object $brandId = this.getBrandId();
        result = result * PRIME + ($brandId == null ? 43 : $brandId.hashCode());
        final Object $brandName = this.getBrandName();
        result = result * PRIME + ($brandName == null ? 43 : $brandName.hashCode());
        final Object $brandLogoUrl = this.getBrandLogoUrl();
        result = result * PRIME + ($brandLogoUrl == null ? 43 : $brandLogoUrl.hashCode());
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
        final Object $hotScore = this.getHotScore();
        result = result * PRIME + ($hotScore == null ? 43 : $hotScore.hashCode());
        final Object $platformProperty = this.getPlatformProperty();
        result = result * PRIME + ($platformProperty == null ? 43 : $platformProperty.hashCode());
        return result;
    }

    public String toString() {
        return "Product(id=" + this.getId() + ", defaultImage=" + this.getDefaultImage() + ", productName=" + this.getProductName() + ", price=" + this.getPrice() + ", createTime=" + this.getCreateTime() + ", brandId=" + this.getBrandId() + ", brandName=" + this.getBrandName() + ", brandLogoUrl=" + this.getBrandLogoUrl() + ", category1Id=" + this.getCategory1Id() + ", category1Name=" + this.getCategory1Name() + ", category2Id=" + this.getCategory2Id() + ", category2Name=" + this.getCategory2Name() + ", category3Id=" + this.getCategory3Id() + ", category3Name=" + this.getCategory3Name() + ", hotScore=" + this.getHotScore() + ", platformProperty=" + this.getPlatformProperty() + ")";
    }
}
