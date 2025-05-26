package com.microservices.categoryservice.exception;

import lombok.Getter;

@Getter
public class CategoryServiceException extends Exception {
    private String errorCode;

    public CategoryServiceException(String msg) {
        super(msg);

    }
    public CategoryServiceException(String msg, String errCode) {
        super(msg);
        this.errorCode = errCode;
    }
}
