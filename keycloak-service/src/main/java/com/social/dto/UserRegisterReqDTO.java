package com.social.dto;

public record UserRegisterReqDTO(
        String username,
        String firstName,
        String lastName,
        String email,
        String password
) {
}
