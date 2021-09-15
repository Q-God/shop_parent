package com.gmall.search;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class SearchPlatformProperty {
    //平台属性Id
    @Field(type = FieldType.Long)
    private Long propertyKeyId;
    //平台属性值
    @Field(type = FieldType.Keyword)
    private String propertyValue;
    //平台属性名称
    @Field(type = FieldType.Keyword)
    private String propertyKey;

    public SearchPlatformProperty() {
    }

    public Long getPropertyKeyId() {
        return this.propertyKeyId;
    }

    public String getPropertyValue() {
        return this.propertyValue;
    }

    public String getPropertyKey() {
        return this.propertyKey;
    }

    public void setPropertyKeyId(Long propertyKeyId) {
        this.propertyKeyId = propertyKeyId;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public void setPropertyKey(String propertyKey) {
        this.propertyKey = propertyKey;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof SearchPlatformProperty)) return false;
        final SearchPlatformProperty other = (SearchPlatformProperty) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$propertyKeyId = this.getPropertyKeyId();
        final Object other$propertyKeyId = other.getPropertyKeyId();
        if (this$propertyKeyId == null ? other$propertyKeyId != null : !this$propertyKeyId.equals(other$propertyKeyId))
            return false;
        final Object this$propertyValue = this.getPropertyValue();
        final Object other$propertyValue = other.getPropertyValue();
        if (this$propertyValue == null ? other$propertyValue != null : !this$propertyValue.equals(other$propertyValue))
            return false;
        final Object this$propertyKey = this.getPropertyKey();
        final Object other$propertyKey = other.getPropertyKey();
        if (this$propertyKey == null ? other$propertyKey != null : !this$propertyKey.equals(other$propertyKey))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof SearchPlatformProperty;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $propertyKeyId = this.getPropertyKeyId();
        result = result * PRIME + ($propertyKeyId == null ? 43 : $propertyKeyId.hashCode());
        final Object $propertyValue = this.getPropertyValue();
        result = result * PRIME + ($propertyValue == null ? 43 : $propertyValue.hashCode());
        final Object $propertyKey = this.getPropertyKey();
        result = result * PRIME + ($propertyKey == null ? 43 : $propertyKey.hashCode());
        return result;
    }

    public String toString() {
        return "SearchPlatformProperty(propertyKeyId=" + this.getPropertyKeyId() + ", propertyValue=" + this.getPropertyValue() + ", propertyKey=" + this.getPropertyKey() + ")";
    }
}
