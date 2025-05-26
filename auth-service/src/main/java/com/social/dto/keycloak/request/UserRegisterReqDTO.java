package com.social.dto.keycloak.request;

import lombok.Builder;

@Builder
public record UserRegisterReqDTO(
        String username,
        String email,
        String password,
        String firstName,
        String lastName,
        String phone
) {
}
