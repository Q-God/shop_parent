package com.gmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户地址表
 * </p>
 *
 * @author 尚硅谷张强
 * @since 2021-07-30
 */
@Accessors(chain = true)
@TableName("user_address")
public class UserAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户地址
     */
    private String userAddress;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 收件人
     */
    private String consignee;

    /**
     * 联系方式
     */
    private String phoneNum;

    /**
     * 是否是默认
     */
    private String isDefault;

    public UserAddress() {
    }


    public Long getId() {
        return this.id;
    }

    public String getUserAddress() {
        return this.userAddress;
    }

    public Long getUserId() {
        return this.userId;
    }

    public String getConsignee() {
        return this.consignee;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public String getIsDefault() {
        return this.isDefault;
    }

    public UserAddress setId(Long id) {
        this.id = id;
        return this;
    }

    public UserAddress setUserAddress(String userAddress) {
        this.userAddress = userAddress;
        return this;
    }

    public UserAddress setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public UserAddress setConsignee(String consignee) {
        this.consignee = consignee;
        return this;
    }

    public UserAddress setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
        return this;
    }

    public UserAddress setIsDefault(String isDefault) {
        this.isDefault = isDefault;
        return this;
    }

    public String toString() {
        return "UserAddress(id=" + this.getId() + ", userAddress=" + this.getUserAddress() + ", userId=" + this.getUserId() + ", consignee=" + this.getConsignee() + ", phoneNum=" + this.getPhoneNum() + ", isDefault=" + this.getIsDefault() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserAddress)) return false;
        final UserAddress other = (UserAddress) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$userAddress = this.getUserAddress();
        final Object other$userAddress = other.getUserAddress();
        if (this$userAddress == null ? other$userAddress != null : !this$userAddress.equals(other$userAddress))
            return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        final Object this$consignee = this.getConsignee();
        final Object other$consignee = other.getConsignee();
        if (this$consignee == null ? other$consignee != null : !this$consignee.equals(other$consignee)) return false;
        final Object this$phoneNum = this.getPhoneNum();
        final Object other$phoneNum = other.getPhoneNum();
        if (this$phoneNum == null ? other$phoneNum != null : !this$phoneNum.equals(other$phoneNum)) return false;
        final Object this$isDefault = this.getIsDefault();
        final Object other$isDefault = other.getIsDefault();
        if (this$isDefault == null ? other$isDefault != null : !this$isDefault.equals(other$isDefault)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserAddress;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $userAddress = this.getUserAddress();
        result = result * PRIME + ($userAddress == null ? 43 : $userAddress.hashCode());
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $consignee = this.getConsignee();
        result = result * PRIME + ($consignee == null ? 43 : $consignee.hashCode());
        final Object $phoneNum = this.getPhoneNum();
        result = result * PRIME + ($phoneNum == null ? 43 : $phoneNum.hashCode());
        final Object $isDefault = this.getIsDefault();
        result = result * PRIME + ($isDefault == null ? 43 : $isDefault.hashCode());
        return result;
    }
}
