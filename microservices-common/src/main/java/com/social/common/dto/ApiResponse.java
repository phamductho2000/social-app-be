//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.social.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
//import com.paypay.msg.RespMsg;
import lombok.Generated;

public class ApiResponse<T> {
    private static final String SUCCESS_CODE_TEXT = "SUCCESS";
    private String code;
    private String message;
    @JsonInclude(Include.NON_NULL)
    private T data = null;

    public ApiResponse() {
    }

    public ApiResponse(String resCode) {
        this.code = resCode;
//        this.message = RespMsg.getMessage(resCode);
    }

    public ApiResponse(String code, T data) {
        this.code = code;
//        this.message = RespMsg.getMessage(code);
        this.data = data;
    }

    public ApiResponse(String code, String defaultMessage) {
        this.code = code;
        this.message = defaultMessage;
    }

    public ApiResponse(Enum<?> code) {
        this.code = code.name();
//        this.message = RespMsg.getMessage(code.name());
    }

    public ApiResponse(Enum<?> code, T data) {
        this.code = code.name();
//        this.message = RespMsg.getMessage(code.name());
        this.data = data;
    }

    public ApiResponse(Enum<?> code, String defaultMessage) {
        this.code = code.name();
        this.message = defaultMessage;
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("SUCCESS", data);
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>("SUCCESS");
    }

    public static <T> ApiResponse<T> error(Enum<?> code, T data) {
        return new ApiResponse<>(code, data);
    }

    public static <T> ApiResponse<T> error(Enum<?> code) {
        return new ApiResponse<>(code);
    }

    public static <T> ApiResponse<T> error(Enum<?> code, String defaultMessage) {
        return new ApiResponse<>(code, defaultMessage);
    }

    public static <T> ApiResponse<T> error(String code) {
        return new ApiResponse<>(code);
    }

    public static <T> ApiResponse<T> error(String code, String defaultMessage) {
        return new ApiResponse<>(code, defaultMessage);
    }

    @JsonIgnore
    public boolean isSuccess() {
        return "SUCCESS".equals(this.code);
    }

    @Generated
    public String getCode() {
        return this.code;
    }

    @Generated
    public String getMessage() {
        return this.message;
    }

    @Generated
    public T getData() {
        return this.data;
    }

    @Generated
    public void setCode(final String code) {
        this.code = code;
    }

    @Generated
    public void setMessage(final String message) {
        this.message = message;
    }

    @Generated
    public void setData(final T data) {
        this.data = data;
    }

    @Generated
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ApiResponse)) {
            return false;
        } else {
            ApiResponse<?> other = (ApiResponse)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$code = this.getCode();
                    Object other$code = other.getCode();
                    if (this$code == null) {
                        if (other$code == null) {
                            break label47;
                        }
                    } else if (this$code.equals(other$code)) {
                        break label47;
                    }

                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    @Generated
    protected boolean canEqual(final Object other) {
        return other instanceof ApiResponse;
    }

    @Generated
    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    @Generated
    public String toString() {
        String var10000 = this.getCode();
        return "ApiResponse(code=" + var10000 + ", message=" + this.getMessage() + ", data=" + String.valueOf(this.getData()) + ")";
    }

    @Generated
    public ApiResponse(final String code, final String message, final T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
