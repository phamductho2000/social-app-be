package com.social.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.social.common.dto.ApiResponse;
import com.social.common.exception.AppException;
import com.social.auth.dto.keycloak.request.UserLoginRequestDto;
import com.social.auth.dto.keycloak.request.UserRegisterReqDTO;
import com.social.auth.dto.keycloak.response.UserLoginResponseDto;
import com.social.auth.dto.user.response.UserResponseDTO;
import com.social.auth.service.UserService;
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

    @PostMapping("/login")
    public ApiResponse<UserLoginResponseDto> login(@RequestBody UserLoginRequestDto request) throws JsonProcessingException {
        return ApiResponse.success(userService.login(request));
    }

    @PostMapping("/register")
    public ApiResponse<UserResponseDTO> register(@RequestBody UserRegisterReqDTO request) throws AppException {
        return ApiResponse.success(userService.register(request));
    }
}
