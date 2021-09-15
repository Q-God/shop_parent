package com.gmall.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author 尚硅谷张强
 * @since 2021-07-30
 */
@Accessors(chain = true)
@TableName("user_info")
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名称
     */
    private String loginName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户密码
     */
    private String passwd;

    /**
     * 用户姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phoneNum;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 头像
     */
    private String headImg;

    /**
     * 用户级别
     */
    private String userLevel;

    public UserInfo() {
    }


    public Long getId() {
        return this.id;
    }

    public String getLoginName() {
        return this.loginName;
    }

    public String getNickName() {
        return this.nickName;
    }

    public String getPasswd() {
        return this.passwd;
    }

    public String getName() {
        return this.name;
    }

    public String getPhoneNum() {
        return this.phoneNum;
    }

    public String getEmail() {
        return this.email;
    }

    public String getHeadImg() {
        return this.headImg;
    }

    public String getUserLevel() {
        return this.userLevel;
    }

    public UserInfo setId(Long id) {
        this.id = id;
        return this;
    }

    public UserInfo setLoginName(String loginName) {
        this.loginName = loginName;
        return this;
    }

    public UserInfo setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public UserInfo setPasswd(String passwd) {
        this.passwd = passwd;
        return this;
    }

    public UserInfo setName(String name) {
        this.name = name;
        return this;
    }

    public UserInfo setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
        return this;
    }

    public UserInfo setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserInfo setHeadImg(String headImg) {
        this.headImg = headImg;
        return this;
    }

    public UserInfo setUserLevel(String userLevel) {
        this.userLevel = userLevel;
        return this;
    }

    public String toString() {
        return "UserInfo(id=" + this.getId() + ", loginName=" + this.getLoginName() + ", nickName=" + this.getNickName() + ", passwd=" + this.getPasswd() + ", name=" + this.getName() + ", phoneNum=" + this.getPhoneNum() + ", email=" + this.getEmail() + ", headImg=" + this.getHeadImg() + ", userLevel=" + this.getUserLevel() + ")";
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserInfo)) return false;
        final UserInfo other = (UserInfo) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$loginName = this.getLoginName();
        final Object other$loginName = other.getLoginName();
        if (this$loginName == null ? other$loginName != null : !this$loginName.equals(other$loginName)) return false;
        final Object this$nickName = this.getNickName();
        final Object other$nickName = other.getNickName();
        if (this$nickName == null ? other$nickName != null : !this$nickName.equals(other$nickName)) return false;
        final Object this$passwd = this.getPasswd();
        final Object other$passwd = other.getPasswd();
        if (this$passwd == null ? other$passwd != null : !this$passwd.equals(other$passwd)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$phoneNum = this.getPhoneNum();
        final Object other$phoneNum = other.getPhoneNum();
        if (this$phoneNum == null ? other$phoneNum != null : !this$phoneNum.equals(other$phoneNum)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final Object this$headImg = this.getHeadImg();
        final Object other$headImg = other.getHeadImg();
        if (this$headImg == null ? other$headImg != null : !this$headImg.equals(other$headImg)) return false;
        final Object this$userLevel = this.getUserLevel();
        final Object other$userLevel = other.getUserLevel();
        if (this$userLevel == null ? other$userLevel != null : !this$userLevel.equals(other$userLevel)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserInfo;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $loginName = this.getLoginName();
        result = result * PRIME + ($loginName == null ? 43 : $loginName.hashCode());
        final Object $nickName = this.getNickName();
        result = result * PRIME + ($nickName == null ? 43 : $nickName.hashCode());
        final Object $passwd = this.getPasswd();
        result = result * PRIME + ($passwd == null ? 43 : $passwd.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $phoneNum = this.getPhoneNum();
        result = result * PRIME + ($phoneNum == null ? 43 : $phoneNum.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $headImg = this.getHeadImg();
        result = result * PRIME + ($headImg == null ? 43 : $headImg.hashCode());
        final Object $userLevel = this.getUserLevel();
        result = result * PRIME + ($userLevel == null ? 43 : $userLevel.hashCode());
        return result;
    }
}
