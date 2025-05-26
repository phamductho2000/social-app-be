package com.social.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.social.conversation.dto.ApiResponse;
import com.social.dto.keycloak.request.UserLoginRequestDto;
import com.social.dto.keycloak.request.UserRegisterReqDTO;
import com.social.dto.keycloak.response.UserLoginResponseDto;
import com.social.dto.user.response.UserResponseDTO;
import com.social.conversation.exception.AppException;
import com.social.service.UserService;
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
        return userService.login(request);
    }

    @PostMapping("/register")
    public ApiResponse<UserResponseDTO> register(@RequestBody UserRegisterReqDTO request) throws AppException {
        return userService.register(request);
    }
}
