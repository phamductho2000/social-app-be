package com.social.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.social.conversation.dto.ApiResponse;
import com.social.dto.keycloak.request.UserLoginRequestDto;
import com.social.dto.keycloak.request.UserRegisterReqDTO;
import com.social.dto.keycloak.response.UserLoginResponseDto;
import com.social.dto.user.response.UserResponseDTO;
import com.social.conversation.exception.AppException;

public interface UserService {
    ApiResponse<UserLoginResponseDto> login(UserLoginRequestDto userLoginRequestDto) throws JsonProcessingException;

    ApiResponse<UserResponseDTO> register(UserRegisterReqDTO request) throws AppException;
}
