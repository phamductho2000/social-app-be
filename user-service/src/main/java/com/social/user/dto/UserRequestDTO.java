package com.social.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDTO {
    private String id;
    private String userId;
    private String userName;
    private String password;
    private Boolean enabled;
    private String firstName;
    private String lastName;
    private String email;
    private Instant birthDate;
    private List<String> userIds;
}
