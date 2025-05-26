package com.social.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginResponseDto {

  private String accessToken;
  private String refreshToken;
  private String expireIn;
  private String refreshExpiresIn;
}
