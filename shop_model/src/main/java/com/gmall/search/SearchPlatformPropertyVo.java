package com.gmall.search;

import com.gmall.enums.SearchPlatformPropertyVoSingleton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// 平台属性相关对象
public class SearchPlatformPropertyVo implements Serializable {

    // 平台属性Id
    private Long propertyKeyId;
    //当前属性值的集合
    private List<String> propertyValueList = new ArrayList<>();
    //属性名称
    private String propertyKey;

    public SearchPlatformPropertyVo() {
    }

    public static SearchPlatformPropertyVo getInstance() {
        return SearchPlatformPropertyVoSingleton.INSTANCE.getInstance();
    }

    public Long getPropertyKeyId() {
        return this.propertyKeyId;
    }

    public List<String> getPropertyValueList() {
        return this.propertyValueList;
    }

    public String getPropertyKey() {
        return this.propertyKey;
    }

    public void setPropertyKeyId(Long propertyKeyId) {
        this.propertyKeyId = propertyKeyId;
    }

    public void setPropertyValueList(List<String> propertyValueList) {
        this.propertyValueList = propertyValueList;
    }

    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SearchPlatformPropertyVo)) return false;
        final SearchPlatformPropertyVo other = (SearchPlatformPropertyVo) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$propertyKeyId = this.getPropertyKeyId();
        final Object other$propertyKeyId = other.getPropertyKeyId();
        if (this$propertyKeyId == null ? other$propertyKeyId != null : !this$propertyKeyId.equals(other$propertyKeyId))
            return false;
        final Object this$propertyValueList = this.getPropertyValueList();
        final Object other$propertyValueList = other.getPropertyValueList();
        if (this$propertyValueList == null ? other$propertyValueList != null : !this$propertyValueList.equals(other$propertyValueList))
            return false;
        final Object this$propertyKey = this.getPropertyKey();
        final Object other$propertyKey = other.getPropertyKey();
        if (this$propertyKey == null ? other$propertyKey != null : !this$propertyKey.equals(other$propertyKey))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SearchPlatformPropertyVo;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $propertyKeyId = this.getPropertyKeyId();
        result = result * PRIME + ($propertyKeyId == null ? 43 : $propertyKeyId.hashCode());
        final Object $propertyValueList = this.getPropertyValueList();
        result = result * PRIME + ($propertyValueList == null ? 43 : $propertyValueList.hashCode());
        final Object $propertyKey = this.getPropertyKey();
        result = result * PRIME + ($propertyKey == null ? 43 : $propertyKey.hashCode());
        return result;
    }

    public String toString() {
        return "SearchPlatformPropertyVo(propertyKeyId=" + this.getPropertyKeyId() + ", propertyValueList=" + this.getPropertyValueList() + ", propertyKey=" + this.getPropertyKey() + ")";
    }
}

