package com.social.client;

import com.social.common.dto.ApiResponse;
import com.social.dto.keycloak.request.UserLoginRequestDto;
import com.social.dto.keycloak.response.UserLoginResponseDto;
import com.social.dto.keycloak.request.UserRegisterReqDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "keycloak-service")
public interface KeycloakClient {

    @PostMapping(value = "/api/user/login", consumes = "application/json")
    ApiResponse<UserLoginResponseDto> login(UserLoginRequestDto userLoginRequestDto);

    @PostMapping(value = "/api/user/create-user", consumes = "application/json")
    ApiResponse<Boolean> createUser(UserRegisterReqDTO userRegisterReqDTO);

    @PostMapping(value = "/api/user/delete-user/{email}", consumes = "application/json")
    ApiResponse<Boolean> deleteUser(@PathVariable String email);
}