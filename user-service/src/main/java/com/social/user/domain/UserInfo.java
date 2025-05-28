package com.social.user.domain;

import com.social.common.domain.BaseDomain;
import com.social.user.constants.UserStatus;
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

    private String userId;

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