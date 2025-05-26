package com.social.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private String id;

    private String username;

    private String firstName;

    private String lastName;

    private String fullName;

    private String email;

    private LocalDateTime createdAt;

}
