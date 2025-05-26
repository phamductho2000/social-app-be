package com.social.service;

import com.social.common.page.CustomPageScroll;
import com.social.dto.UserRequestDTO;
import com.social.dto.UserResponseDTO;
import com.social.dto.request.SearchUserRequestDto;
import com.social.exception.UserServiceException;

import java.util.List;

public interface UserService {
    UserResponseDTO create(UserRequestDTO request) throws UserServiceException;

    UserResponseDTO update(UserRequestDTO request) throws UserServiceException;

    CustomPageScroll<UserResponseDTO> getScrollUsers(SearchUserRequestDto request) throws UserServiceException;

    UserResponseDTO getUserById(String id) throws UserServiceException;

    UserResponseDTO getUserByUsername(String username) throws UserServiceException;

    List<UserResponseDTO> getUsersByIds(List<String> ids) throws UserServiceException;
}
