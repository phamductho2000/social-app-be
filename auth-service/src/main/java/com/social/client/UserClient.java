package com.social.client;

import com.social.conversation.dto.ApiResponse;
import com.social.dto.keycloak.request.UserRegisterReqDTO;
import com.social.dto.user.response.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "user-service")
public interface UserClient {

    @PostMapping(value = "/api/user/create", consumes = "application/json")
    ApiResponse<UserResponseDTO> createUser(UserRegisterReqDTO userRegisterReqDTO);
}