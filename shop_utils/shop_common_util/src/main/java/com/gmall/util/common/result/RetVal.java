package com.gmall.util.common.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 全局统一返回结果类
 *
 */
@ApiModel(value = "全局统一返回结果")
public class RetVal<T> {

    // 200 , 成功!
    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private T data;

    public RetVal(){}

    // 返回数据
    protected static <T> RetVal<T> build(T data) {
        RetVal<T> retVal = new RetVal<T>();
        if (data != null)
            retVal.setData(data);
        return retVal;
    }

    public static <T> RetVal<T> build(T body, RetValCodeEnum retValCodeEnum) {
        RetVal<T> retVal = build(body);
        retVal.setCode(retValCodeEnum.getCode());
        retVal.setMessage(retValCodeEnum.getMessage());
        return retVal;
    }

    public static<T> RetVal<T> ok(){
        return RetVal.ok(null);
    }

    /**
     * 操作成功
     * @param data
     * @param <T>
     * @return
     */
    public static<T> RetVal<T> ok(T data){
        RetVal<T> retVal = build(data);
        return build(data, RetValCodeEnum.SUCCESS);
    }

    public static<T> RetVal<T> fail(){
        return RetVal.fail(null);
    }

    /**
     * 操作失败
     * @param data
     * @param <T>
     * @return
     */
    public static<T> RetVal<T> fail(T data){
        RetVal<T> retVal = build(data);
        return build(data, RetValCodeEnum.FAIL);
    }

    public RetVal<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public RetVal<T> code(Integer code){
        this.setCode(code);
        return this;
    }

    public boolean isOk() {
        if(this.getCode().intValue() == RetValCodeEnum.SUCCESS.getCode().intValue()) {
            return true;
        }
        return false;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof RetVal)) return false;
        final RetVal<?> other = (RetVal<?>) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$code = this.getCode();
        final Object other$code = other.getCode();
        if (this$code == null ? other$code != null : !this$code.equals(other$code)) return false;
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        final Object this$data = this.getData();
        final Object other$data = other.getData();
        if (this$data == null ? other$data != null : !this$data.equals(other$data)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof RetVal;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $code = this.getCode();
        result = result * PRIME + ($code == null ? 43 : $code.hashCode());
        final Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        final Object $data = this.getData();
        result = result * PRIME + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    public String toString() {
        return "RetVal(code=" + this.getCode() + ", message=" + this.getMessage() + ", data=" + this.getData() + ")";
    }
}
