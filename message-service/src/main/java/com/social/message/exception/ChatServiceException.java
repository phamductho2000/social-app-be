package com.social.message.exception;

import lombok.Getter;

@Getter
public class ChatServiceException extends Exception {
    private String errorCode;

    public ChatServiceException(String msg) {
        super(msg);

    }
    public ChatServiceException(String msg, String errCode) {
        super(msg);
        this.errorCode = errCode;
    }
}
