package com.social.user.service;

import com.social.common.page.CustomPageScroll;
import com.social.user.dto.UserRequestDTO;
import com.social.user.dto.UserResponseDTO;
import com.social.user.dto.request.SearchUserRequestDto;
import com.social.user.exception.UserServiceException;

import java.util.List;

public interface UserService {
    UserResponseDTO create(UserRequestDTO request) throws UserServiceException;

    UserResponseDTO update(UserRequestDTO request) throws UserServiceException;

    CustomPageScroll<UserResponseDTO> getScrollUsers(SearchUserRequestDto request) throws UserServiceException;

    UserResponseDTO getUserById(String id) throws UserServiceException;

    UserResponseDTO getUserByUsername(String username) throws UserServiceException;

    List<UserResponseDTO> getUsersByIds(List<String> ids) throws UserServiceException;
}
