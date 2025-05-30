package com.social.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.social.auth.dto.keycloak.request.UserLoginRequestDto;
import com.social.auth.dto.keycloak.request.UserRegisterReqDTO;
import com.social.auth.dto.keycloak.response.UserLoginResponseDto;
import com.social.auth.dto.user.response.UserResponseDTO;
import com.social.common.exception.AppException;

public interface UserService {
    UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto) throws JsonProcessingException, AppException;

    UserResponseDTO register(UserRegisterReqDTO request) throws AppException;
}
