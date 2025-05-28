package com.social.auth.constants;

import lombok.Getter;

@Getter
public enum RespCode {
  SUCCESS("SUCCESS"),
  PROCESSING("PROCESSING"),
  ERR_UNAUTHORIZED("ERR_UNAUTHORIZED"),
  ERR_DUPLICATE("ERR_DUPLICATE"),
  NOT_FOUND("NOT_FOUND"),
  ERR_SYSTEM("ERR_SYSTEM"),
  ERR_DATA_INVALID("ERR_DATA_INVALID"),
  ERR_AUTH_OTP_INVALID("ERR_AUTH_OTP_INVALID"),
  OTP_INVALID("OTP_INVALID"),
  ERR_AUTH_REQUEST_IS_NOT_ACCEPTED("ERR_AUTH_REQUEST_IS_NOT_ACCEPTED");

  private final String code;

  RespCode(String code) {
    this.code = code;
  }
}
