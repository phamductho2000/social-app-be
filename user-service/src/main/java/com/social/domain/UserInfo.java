package com.social.domain;

import com.social.constants.UserStatus;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "user_info")
public class UserInfo extends BaseDomain {

    @Id
    private String id;

    @NotNull
    private String userName;

    private String firstName;

    private String lastName;

    private String fullName;

    @NotNull
    private String email;

    @NotNull
    private UserStatus status;

    private String phone;
}