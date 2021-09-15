package com.gmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 库存单元表
 * </p>
 *
 * @author zhangqiang
 * @since 2021-03-24
 */
@Accessors(chain = true)
@TableName("sku_info")
@ApiModel(value="SkuInfo对象", description="库存单元表")
public class SkuInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "库存id(itemID)")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "商品id")
    private Long productId;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "sku名称")
    private String skuName;

    @ApiModelProperty(value = "商品规格描述")
    private String skuDesc;

    @ApiModelProperty(value = "重量")
    private BigDecimal weight;

    @ApiModelProperty(value = "品牌(冗余)")
    private Long brandId;

    @ApiModelProperty(value = "三级分类id（冗余)")
    private Long category3Id;

    @ApiModelProperty(value = "默认显示图片(冗余)")
    private String skuDefaultImg;

    @ApiModelProperty(value = "是否销售（1：是 0：否）")
    private Integer isSale;

    @TableField(exist = false)
    List<SkuImage> skuImageList;

    @TableField(exist = false)
    List<SkuPlatformPropertyValue> skuPlatformPropertyValueList;

    @TableField(exist = false)
    List<SkuSalePropertyValue> skuSalePropertyValueList;

    public SkuInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public Long getProductId() {
        return this.productId;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public String getSkuName() {
        return this.skuName;
    }

    public String getSkuDesc() {
        return this.skuDesc;
    }

    public BigDecimal getWeight() {
        return this.weight;
    }

    public Long getBrandId() {
        return this.brandId;
    }

    public Long getCategory3Id() {
        return this.category3Id;
    }

    public String getSkuDefaultImg() {
        return this.skuDefaultImg;
    }

    public Integer getIsSale() {
        return this.isSale;
    }

    public List<SkuImage> getSkuImageList() {
        return this.skuImageList;
    }

    public List<SkuPlatformPropertyValue> getSkuPlatformPropertyValueList() {
        return this.skuPlatformPropertyValueList;
    }

    public List<SkuSalePropertyValue> getSkuSalePropertyValueList() {
        return this.skuSalePropertyValueList;
    }

    public SkuInfo setId(Long id) {
        this.id = id;
        return this;
    }

    public SkuInfo setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public SkuInfo setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public SkuInfo setSkuName(String skuName) {
        this.skuName = skuName;
        return this;
    }

    public SkuInfo setSkuDesc(String skuDesc) {
        this.skuDesc = skuDesc;
        return this;
    }

    public SkuInfo setWeight(BigDecimal weight) {
        this.weight = weight;
        return this;
    }

    public SkuInfo setBrandId(Long brandId) {
        this.brandId = brandId;
        return this;
    }

    public SkuInfo setCategory3Id(Long category3Id) {
        this.category3Id = category3Id;
        return this;
    }

    public SkuInfo setSkuDefaultImg(String skuDefaultImg) {
        this.skuDefaultImg = skuDefaultImg;
        return this;
    }

    public SkuInfo setIsSale(Integer isSale) {
        this.isSale = isSale;
        return this;
    }

    public SkuInfo setSkuImageList(List<SkuImage> skuImageList) {
        this.skuImageList = skuImageList;
        return this;
    }

    public SkuInfo setSkuPlatformPropertyValueList(List<SkuPlatformPropertyValue> skuPlatformPropertyValueList) {
        this.skuPlatformPropertyValueList = skuPlatformPropertyValueList;
        return this;
    }

    public SkuInfo setSkuSalePropertyValueList(List<SkuSalePropertyValue> skuSalePropertyValueList) {
        this.skuSalePropertyValueList = skuSalePropertyValueList;
        return this;
    }

    public String toString() {
        return "SkuInfo(id=" + this.getId() + ", productId=" + this.getProductId() + ", price=" + this.getPrice() + ", skuName=" + this.getSkuName() + ", skuDesc=" + this.getSkuDesc() + ", weight=" + this.getWeight() + ", brandId=" + this.getBrandId() + ", category3Id=" + this.getCategory3Id() + ", skuDefaultImg=" + this.getSkuDefaultImg() + ", isSale=" + this.getIsSale() + ", skuImageList=" + this.getSkuImageList() + ", skuPlatformPropertyValueList=" + this.getSkuPlatformPropertyValueList() + ", skuSalePropertyValueList=" + this.getSkuSalePropertyValueList() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SkuInfo)) return false;
        final SkuInfo other = (SkuInfo) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$productId = this.getProductId();
        final Object other$productId = other.getProductId();
        if (this$productId == null ? other$productId != null : !this$productId.equals(other$productId)) return false;
        final Object this$price = this.getPrice();
        final Object other$price = other.getPrice();
        if (this$price == null ? other$price != null : !this$price.equals(other$price)) return false;
        final Object this$skuName = this.getSkuName();
        final Object other$skuName = other.getSkuName();
        if (this$skuName == null ? other$skuName != null : !this$skuName.equals(other$skuName)) return false;
        final Object this$skuDesc = this.getSkuDesc();
        final Object other$skuDesc = other.getSkuDesc();
        if (this$skuDesc == null ? other$skuDesc != null : !this$skuDesc.equals(other$skuDesc)) return false;
        final Object this$weight = this.getWeight();
        final Object other$weight = other.getWeight();
        if (this$weight == null ? other$weight != null : !this$weight.equals(other$weight)) return false;
        final Object this$brandId = this.getBrandId();
        final Object other$brandId = other.getBrandId();
        if (this$brandId == null ? other$brandId != null : !this$brandId.equals(other$brandId)) return false;
        final Object this$category3Id = this.getCategory3Id();
        final Object other$category3Id = other.getCategory3Id();
        if (this$category3Id == null ? other$category3Id != null : !this$category3Id.equals(other$category3Id))
            return false;
        final Object this$skuDefaultImg = this.getSkuDefaultImg();
        final Object other$skuDefaultImg = other.getSkuDefaultImg();
        if (this$skuDefaultImg == null ? other$skuDefaultImg != null : !this$skuDefaultImg.equals(other$skuDefaultImg))
            return false;
        final Object this$isSale = this.getIsSale();
        final Object other$isSale = other.getIsSale();
        if (this$isSale == null ? other$isSale != null : !this$isSale.equals(other$isSale)) return false;
        final Object this$skuImageList = this.getSkuImageList();
        final Object other$skuImageList = other.getSkuImageList();
        if (this$skuImageList == null ? other$skuImageList != null : !this$skuImageList.equals(other$skuImageList))
            return false;
        final Object this$skuPlatformPropertyValueList = this.getSkuPlatformPropertyValueList();
        final Object other$skuPlatformPropertyValueList = other.getSkuPlatformPropertyValueList();
        if (this$skuPlatformPropertyValueList == null ? other$skuPlatformPropertyValueList != null : !this$skuPlatformPropertyValueList.equals(other$skuPlatformPropertyValueList))
            return false;
        final Object this$skuSalePropertyValueList = this.getSkuSalePropertyValueList();
        final Object other$skuSalePropertyValueList = other.getSkuSalePropertyValueList();
        if (this$skuSalePropertyValueList == null ? other$skuSalePropertyValueList != null : !this$skuSalePropertyValueList.equals(other$skuSalePropertyValueList))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SkuInfo;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $productId = this.getProductId();
        result = result * PRIME + ($productId == null ? 43 : $productId.hashCode());
        final Object $price = this.getPrice();
        result = result * PRIME + ($price == null ? 43 : $price.hashCode());
        final Object $skuName = this.getSkuName();
        result = result * PRIME + ($skuName == null ? 43 : $skuName.hashCode());
        final Object $skuDesc = this.getSkuDesc();
        result = result * PRIME + ($skuDesc == null ? 43 : $skuDesc.hashCode());
        final Object $weight = this.getWeight();
        result = result * PRIME + ($weight == null ? 43 : $weight.hashCode());
        final Object $brandId = this.getBrandId();
        result = result * PRIME + ($brandId == null ? 43 : $brandId.hashCode());
        final Object $category3Id = this.getCategory3Id();
        result = result * PRIME + ($category3Id == null ? 43 : $category3Id.hashCode());
        final Object $skuDefaultImg = this.getSkuDefaultImg();
        result = result * PRIME + ($skuDefaultImg == null ? 43 : $skuDefaultImg.hashCode());
        final Object $isSale = this.getIsSale();
        result = result * PRIME + ($isSale == null ? 43 : $isSale.hashCode());
        final Object $skuImageList = this.getSkuImageList();
        result = result * PRIME + ($skuImageList == null ? 43 : $skuImageList.hashCode());
        final Object $skuPlatformPropertyValueList = this.getSkuPlatformPropertyValueList();
        result = result * PRIME + ($skuPlatformPropertyValueList == null ? 43 : $skuPlatformPropertyValueList.hashCode());
        final Object $skuSalePropertyValueList = this.getSkuSalePropertyValueList();
        result = result * PRIME + ($skuSalePropertyValueList == null ? 43 : $skuSalePropertyValueList.hashCode());
        return result;
    }
}
