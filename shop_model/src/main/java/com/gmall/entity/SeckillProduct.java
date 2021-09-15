package com.gmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author 尚硅谷张强
 * @since 2021-08-03
 */
@Accessors(chain = true)
@TableName("seckill_product")
public class SeckillProduct implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * product_id
     */
    private Long productId;

    /**
     * sku_id
     */
    private Long skuId;

    /**
     * 标题
     */
    private String skuName;

    /**
     * 商品图片
     */
    private String skuDefaultImg;

    /**
     * 原价格
     */
    private BigDecimal price;

    /**
     * 秒杀价格
     */
    private BigDecimal costPrice;

    /**
     * 添加日期
     */
    private Date createTime;

    /**
     * 审核日期
     */
    private Date checkTime;

    /**
     * 审核状态 1为秒杀商品 2为结束 3审核未通过
     */
    private String status;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 秒杀商品数
     */
    private Integer num;

    /**
     * 剩余库存数
     */
    private Integer stockCount;

    /**
     * 描述
     */
    private String skuDesc;

    public SeckillProduct() {
    }


    public Long getId() {
        return this.id;
    }

    public Long getProductId() {
        return this.productId;
    }

    public Long getSkuId() {
        return this.skuId;
    }

    public String getSkuName() {
        return this.skuName;
    }

    public String getSkuDefaultImg() {
        return this.skuDefaultImg;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public BigDecimal getCostPrice() {
        return this.costPrice;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public Date getCheckTime() {
        return this.checkTime;
    }

    public String getStatus() {
        return this.status;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public Integer getNum() {
        return this.num;
    }

    public Integer getStockCount() {
        return this.stockCount;
    }

    public String getSkuDesc() {
        return this.skuDesc;
    }

    public SeckillProduct setId(Long id) {
        this.id = id;
        return this;
    }

    public SeckillProduct setProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public SeckillProduct setSkuId(Long skuId) {
        this.skuId = skuId;
        return this;
    }

    public SeckillProduct setSkuName(String skuName) {
        this.skuName = skuName;
        return this;
    }

    public SeckillProduct setSkuDefaultImg(String skuDefaultImg) {
        this.skuDefaultImg = skuDefaultImg;
        return this;
    }

    public SeckillProduct setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public SeckillProduct setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
        return this;
    }

    public SeckillProduct setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public SeckillProduct setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
        return this;
    }

    public SeckillProduct setStatus(String status) {
        this.status = status;
        return this;
    }

    public SeckillProduct setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public SeckillProduct setEndTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    public SeckillProduct setNum(Integer num) {
        this.num = num;
        return this;
    }

    public SeckillProduct setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
        return this;
    }

    public SeckillProduct setSkuDesc(String skuDesc) {
        this.skuDesc = skuDesc;
        return this;
    }

    public String toString() {
        return "SeckillProduct(id=" + this.getId() + ", productId=" + this.getProductId() + ", skuId=" + this.getSkuId() + ", skuName=" + this.getSkuName() + ", skuDefaultImg=" + this.getSkuDefaultImg() + ", price=" + this.getPrice() + ", costPrice=" + this.getCostPrice() + ", createTime=" + this.getCreateTime() + ", checkTime=" + this.getCheckTime() + ", status=" + this.getStatus() + ", startTime=" + this.getStartTime() + ", endTime=" + this.getEndTime() + ", num=" + this.getNum() + ", stockCount=" + this.getStockCount() + ", skuDesc=" + this.getSkuDesc() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SeckillProduct)) return false;
        final SeckillProduct other = (SeckillProduct) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$productId = this.getProductId();
        final Object other$productId = other.getProductId();
        if (this$productId == null ? other$productId != null : !this$productId.equals(other$productId)) return false;
        final Object this$skuId = this.getSkuId();
        final Object other$skuId = other.getSkuId();
        if (this$skuId == null ? other$skuId != null : !this$skuId.equals(other$skuId)) return false;
        final Object this$skuName = this.getSkuName();
        final Object other$skuName = other.getSkuName();
        if (this$skuName == null ? other$skuName != null : !this$skuName.equals(other$skuName)) return false;
        final Object this$skuDefaultImg = this.getSkuDefaultImg();
        final Object other$skuDefaultImg = other.getSkuDefaultImg();
        if (this$skuDefaultImg == null ? other$skuDefaultImg != null : !this$skuDefaultImg.equals(other$skuDefaultImg))
            return false;
        final Object this$price = this.getPrice();
        final Object other$price = other.getPrice();
        if (this$price == null ? other$price != null : !this$price.equals(other$price)) return false;
        final Object this$costPrice = this.getCostPrice();
        final Object other$costPrice = other.getCostPrice();
        if (this$costPrice == null ? other$costPrice != null : !this$costPrice.equals(other$costPrice)) return false;
        final Object this$createTime = this.getCreateTime();
        final Object other$createTime = other.getCreateTime();
        if (this$createTime == null ? other$createTime != null : !this$createTime.equals(other$createTime))
            return false;
        final Object this$checkTime = this.getCheckTime();
        final Object other$checkTime = other.getCheckTime();
        if (this$checkTime == null ? other$checkTime != null : !this$checkTime.equals(other$checkTime)) return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        if (this$status == null ? other$status != null : !this$status.equals(other$status)) return false;
        final Object this$startTime = this.getStartTime();
        final Object other$startTime = other.getStartTime();
        if (this$startTime == null ? other$startTime != null : !this$startTime.equals(other$startTime)) return false;
        final Object this$endTime = this.getEndTime();
        final Object other$endTime = other.getEndTime();
        if (this$endTime == null ? other$endTime != null : !this$endTime.equals(other$endTime)) return false;
        final Object this$num = this.getNum();
        final Object other$num = other.getNum();
        if (this$num == null ? other$num != null : !this$num.equals(other$num)) return false;
        final Object this$stockCount = this.getStockCount();
        final Object other$stockCount = other.getStockCount();
        if (this$stockCount == null ? other$stockCount != null : !this$stockCount.equals(other$stockCount))
            return false;
        final Object this$skuDesc = this.getSkuDesc();
        final Object other$skuDesc = other.getSkuDesc();
        if (this$skuDesc == null ? other$skuDesc != null : !this$skuDesc.equals(other$skuDesc)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SeckillProduct;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $productId = this.getProductId();
        result = result * PRIME + ($productId == null ? 43 : $productId.hashCode());
        final Object $skuId = this.getSkuId();
        result = result * PRIME + ($skuId == null ? 43 : $skuId.hashCode());
        final Object $skuName = this.getSkuName();
        result = result * PRIME + ($skuName == null ? 43 : $skuName.hashCode());
        final Object $skuDefaultImg = this.getSkuDefaultImg();
        result = result * PRIME + ($skuDefaultImg == null ? 43 : $skuDefaultImg.hashCode());
        final Object $price = this.getPrice();
        result = result * PRIME + ($price == null ? 43 : $price.hashCode());
        final Object $costPrice = this.getCostPrice();
        result = result * PRIME + ($costPrice == null ? 43 : $costPrice.hashCode());
        final Object $createTime = this.getCreateTime();
        result = result * PRIME + ($createTime == null ? 43 : $createTime.hashCode());
        final Object $checkTime = this.getCheckTime();
        result = result * PRIME + ($checkTime == null ? 43 : $checkTime.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
        final Object $startTime = this.getStartTime();
        result = result * PRIME + ($startTime == null ? 43 : $startTime.hashCode());
        final Object $endTime = this.getEndTime();
        result = result * PRIME + ($endTime == null ? 43 : $endTime.hashCode());
        final Object $num = this.getNum();
        result = result * PRIME + ($num == null ? 43 : $num.hashCode());
        final Object $stockCount = this.getStockCount();
        result = result * PRIME + ($stockCount == null ? 43 : $stockCount.hashCode());
        final Object $skuDesc = this.getSkuDesc();
        result = result * PRIME + ($skuDesc == null ? 43 : $skuDesc.hashCode());
        return result;
    }
}
