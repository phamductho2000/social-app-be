package com.social.service;

import com.social.client.KeycloakClient;
import com.social.conversation.dto.ApiResponse;
import com.social.dto.UserLoginRequestDto;
import com.social.dto.UserLoginResponseDto;
import com.social.dto.UserRegisterReqDTO;
import com.social.log.Logger;
import com.social.model.AccessTokenModel;
import feign.FeignException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.social.constant.ErrorCode.ERROR_SYSTEM;
import static org.keycloak.OAuth2Constants.*;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    private final KeycloakClient keycloakClient;

    private final Keycloak keycloak;

    private final Logger logger;

    public ApiResponse<UserLoginResponseDto> login(UserLoginRequestDto userLoginRequestDto) {
        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put(GRANT_TYPE, "password");
        headerMap.put(CLIENT_ID, "chat-app");
        headerMap.put(CLIENT_SECRET, "b8lbjvCS8kpTDm9gQefDY1l45O7qurTH");
        headerMap.put("username", userLoginRequestDto.getUsername());
        headerMap.put("password", userLoginRequestDto.getPassword());
        try {
            ResponseEntity<AccessTokenModel> data = keycloakClient.login(headerMap);
//            logger.addTrace("Login data", data);
            AccessTokenModel token = data.getBody();
            if (data.getStatusCode().is2xxSuccessful() && token != null) {
                return ApiResponse.success(UserLoginResponseDto.builder()
                        .accessToken(token.getAccessToken())
                        .refreshToken(token.getRefreshToken())
                        .expireIn(token.getExpiresIn())
                        .refreshExpiresIn(token.getRefreshExpiresIn())
                        .build());

//                return ResponseUtil.wrap()
            }
        } catch (FeignException.FeignClientException ex) {
//            logger.addException(ex);
//            if (ex instanceof FeignException.FeignClientException
//                    && feignException.status() == 401) {
//                return ApiResponse.error(RespCode.ERR_KEYCLOAK_WRONG_USER_OR_PASSWORD);
//            }
        }
//        return ApiResponse.error(ERR_SYSTEM);
        return null;
    }

    public ApiResponse<Boolean> createUser(UserRegisterReqDTO req) {
        UsersResource usersResource = keycloak.realm("app-chat").users();

        UserRepresentation user = new UserRepresentation();
        user.setUsername(req.email());
        user.setEmail(req.email());
        user.setFirstName(req.firstName());
        user.setLastName(req.lastName());
        user.setEnabled(true);

        CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setType("password");
        credentials.setValue(req.password());
        user.setCredentials(Collections.singletonList(credentials));

        Response response = usersResource.create(user);
        if (response.getStatus() == 201) {
            return ApiResponse.success(true);
        }

        return ApiResponse.error(ERROR_SYSTEM, response.readEntity(String.class));
    }

    public ApiResponse<Boolean> deleteUser(String email) {
        List<UserRepresentation> users = keycloak.realm("app-chat")
                .users()
                .search(email);

        if (!users.isEmpty()) {
            String userId = users.get(0).getId();
            keycloak.realm("app-chat").users().delete(userId);
            return ApiResponse.success(true);
        } else {
            return ApiResponse.error(ERROR_SYSTEM);
        }
    }
}
