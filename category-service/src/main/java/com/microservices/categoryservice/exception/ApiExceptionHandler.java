package com.microservices.categoryservice.exception;

import com.microservices.common.util.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity handleAllException(Exception ex, WebRequest request) {
        return ResponseUtil.wrapError(new ErrorMessage(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), ex.getLocalizedMessage()));
    }

    @ExceptionHandler(CategoryServiceException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity handleProductServiceException(CategoryServiceException ex, WebRequest request) {
        return ResponseUtil.wrapError(new ErrorMessage(ex.getErrorCode(), ex.getMessage()));
    }
}
