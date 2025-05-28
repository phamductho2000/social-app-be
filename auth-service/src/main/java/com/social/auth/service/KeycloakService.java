package com.social.auth.service;

import com.social.auth.dto.keycloak.request.UserLoginRequestDto;
import com.social.auth.dto.keycloak.request.UserRegisterReqDTO;
import com.social.auth.dto.keycloak.response.UserLoginResponseDto;
import com.social.common.dto.ApiResponse;
import com.social.common.exception.AppException;

public interface KeycloakService {
    UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto);

    String createUser(UserRegisterReqDTO req) throws AppException;

    ApiResponse<Boolean> deleteUser(String email) throws AppException;
}
