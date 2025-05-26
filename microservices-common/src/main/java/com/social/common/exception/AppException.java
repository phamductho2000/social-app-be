package com.social.common.exception;

import lombok.Getter;

@Getter
public class AppException extends Exception {
    private String errorCode;

    public AppException(String msg) {
        super(msg);

    }
    public AppException(String msg, String errCode) {
        super(msg);
        this.errorCode = errCode;
    }
}
