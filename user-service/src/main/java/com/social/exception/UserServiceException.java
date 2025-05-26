package com.social.exception;

import lombok.Getter;

@Getter
public class UserServiceException extends Exception {
    private String errorCode;

    public UserServiceException(String msg) {
        super(msg);

    }
    public UserServiceException(String msg, String errCode) {
        super(msg);
        this.errorCode = errCode;
    }
}
