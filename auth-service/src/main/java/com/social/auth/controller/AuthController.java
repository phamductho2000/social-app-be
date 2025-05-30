package com.social.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.social.auth.dto.keycloak.request.RefreshTokenRequestDto;
import com.social.auth.dto.keycloak.request.UserLoginRequestDto;
import com.social.auth.dto.keycloak.request.UserRegisterReqDTO;
import com.social.auth.dto.keycloak.response.RefreshTokenResponseDto;
import com.social.auth.dto.keycloak.response.UserLoginResponseDto;
import com.social.auth.dto.user.response.UserResponseDTO;
import com.social.auth.service.KeycloakService;
import com.social.auth.service.UserService;
import com.social.common.dto.ApiResponse;
import com.social.common.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final KeycloakService keycloakService;

    @PostMapping("/login")
    public ApiResponse<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto request) throws JsonProcessingException, AppException {
        return ApiResponse.success(userService.login(request));
    }

    @PostMapping("/register")
    public ApiResponse<UserResponseDTO> register(@RequestBody UserRegisterReqDTO request) throws AppException {
        return ApiResponse.success(userService.register(request));
    }

    @PostMapping("/refreshToken")
    public ApiResponse<RefreshTokenResponseDto> refreshToken(@RequestBody RefreshTokenRequestDto request) throws AppException {
        return ApiResponse.success(keycloakService.refreshToken(request));
    }
}
