package com.social.auth.dto.keycloak.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenResponseDto {

  private String accessToken;
  private String refreshToken;
  private String expireIn;
  private String refreshExpiresIn;
}
