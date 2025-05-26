package com.social.controller;

import com.social.dto.UserLoginRequestDto;
import com.social.dto.UserLoginResponseDto;
import com.social.dto.UserRegisterReqDTO;
import com.social.service.KeycloakService;
import com.social.conversation.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class KeycloakController {

    private final KeycloakService keycloakService;

    @PostMapping("/login")
    public ApiResponse<UserLoginResponseDto> userLogin(@RequestBody UserLoginRequestDto userLoginRequestDTO) {
        return keycloakService.login(userLoginRequestDTO);
    }

    @PostMapping("/create-user")
    public ApiResponse<Boolean> userLogin(@RequestBody UserRegisterReqDTO req) {
        return keycloakService.createUser(req);
    }

    @PostMapping("/delete-user/{email}")
    public ApiResponse<Boolean> deleteUser(@PathVariable String email) {
        return keycloakService.deleteUser(email);
    }
}
