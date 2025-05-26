package com.social.controller;

import com.social.common.dto.ApiResponse;
import com.social.common.page.CustomPageScroll;
import com.social.dto.UserRequestDTO;
import com.social.dto.UserResponseDTO;
import com.social.dto.request.SearchUserRequestDto;
import com.social.exception.UserServiceException;
import com.social.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ApiResponse<UserResponseDTO> save(@RequestBody UserRequestDTO request) throws UserServiceException {
        return ApiResponse.success(userService.create(request));
    }

    @PostMapping("/update")
    public ApiResponse<UserResponseDTO> update(@RequestBody UserRequestDTO request) throws UserServiceException {
        return ApiResponse.success(userService.update(request));
    }

    @PostMapping("/search-users")
    public ApiResponse<CustomPageScroll<UserResponseDTO>> getUsers(@RequestBody SearchUserRequestDto request) throws UserServiceException {
        return ApiResponse.success(userService.getScrollUsers(request));
    }

    @GetMapping("/get-user-by-id/{id}")
    public ApiResponse<UserResponseDTO> getUserById(@PathVariable String id) throws UserServiceException {
        return ApiResponse.success(userService.getUserById(id));
    }

    @GetMapping("/get-user-by-username/{username}")
    public ApiResponse<UserResponseDTO> getUserByUsername(@PathVariable String username) throws UserServiceException {
        return ApiResponse.success(userService.getUserByUsername(username));
    }

    @PostMapping("/get-users-by-ids")
    public ApiResponse<List<UserResponseDTO>> getUsersByIds(@RequestBody List<String> ids) throws UserServiceException {
        return ApiResponse.success(userService.getUsersByIds(ids));
    }
}
