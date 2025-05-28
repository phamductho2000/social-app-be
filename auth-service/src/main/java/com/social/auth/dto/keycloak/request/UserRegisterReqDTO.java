package com.social.auth.dto.keycloak.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRegisterReqDTO {
    String userId;
    String username;
    String email;
    String password;
    String firstName;
    String lastName;
    String phone;
}
