package com.social.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFriendsViewReqDTO {

    private String userFriendId;

    private String userId;

    private String friendId;

    private String username;

    private String firstName;

    private String lastName;

    private String fullName;

    private String email;
}
