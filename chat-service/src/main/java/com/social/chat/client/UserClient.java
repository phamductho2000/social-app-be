package com.social.chat.client;

import com.social.chat.dto.response.UserResponseDTO;
import com.social.common.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "user-service")
public interface UserClient {

    @PostMapping(value = "/api/user/get-users-by-ids", consumes = "application/json")
    ApiResponse<List<UserResponseDTO>> getUsersByIds(@RequestBody List<String> ids);

    @GetMapping(value = "/api/user/get-user-by-id/{id}", consumes = "application/json")
    ApiResponse<UserResponseDTO> getUserById(@PathVariable String id);
}