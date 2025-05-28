package com.social.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccessTokenModel {

  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("expires_in")
  private String expiresIn;
  @JsonProperty("refresh_expires_in")
  private String refreshExpiresIn;
  @JsonProperty("refresh_token")
  private String refreshToken;
  @JsonProperty("token_type")
  private String tokenType;
  @JsonProperty("session_state")
  private String sessionState;
  @JsonProperty("scope")
  private String scope;

}
