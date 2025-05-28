package com.social.auth.client;

import com.social.common.config.FeignConfig;
import com.social.common.dto.ApiResponse;
import com.social.auth.dto.keycloak.request.UserRegisterReqDTO;
import com.social.auth.dto.user.response.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-service", configuration = FeignConfig.class)
public interface UserClient {

    @PostMapping(value = "/api/user/create", consumes = "application/json")
    ApiResponse<UserResponseDTO> createUser(UserRegisterReqDTO userRegisterReqDTO);
}