package com.social.auth.service.impl;

import com.social.auth.client.KeycloakClient;
import com.social.auth.dto.keycloak.request.RefreshTokenRequestDto;
import com.social.auth.dto.keycloak.request.UserLoginRequestDto;
import com.social.auth.dto.keycloak.request.UserRegisterReqDTO;
import com.social.auth.dto.keycloak.response.RefreshTokenResponseDto;
import com.social.auth.dto.keycloak.response.UserLoginResponseDto;
import com.social.auth.model.AccessTokenModel;
import com.social.auth.service.KeycloakService;
import com.social.common.dto.ApiResponse;
import com.social.common.exception.AppException;
import com.social.common.log.Logger;
import feign.FeignException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.social.auth.constants.RespCode.ERR_SYSTEM;
import static org.keycloak.OAuth2Constants.*;

@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {

    private final KeycloakClient keycloakClient;

    private final Keycloak keycloak;

    private final Logger logger;

    @Override
    public UserLoginResponseDto login(UserLoginRequestDto userLoginRequestDto) throws AppException {
        try {
            Map<String, Object> headerMap = new HashMap<>();
            headerMap.put(GRANT_TYPE, PASSWORD);
            headerMap.put(CLIENT_ID, "chat-app");
            headerMap.put(CLIENT_SECRET, "b8lbjvCS8kpTDm9gQefDY1l45O7qurTH");
            headerMap.put(USERNAME, userLoginRequestDto.getUsername());
            headerMap.put(PASSWORD, userLoginRequestDto.getPassword());
            ResponseEntity<AccessTokenModel> data = keycloakClient.login(headerMap);
            AccessTokenModel token = data.getBody();
            if (data.getStatusCode().is2xxSuccessful() && token != null) {
                return UserLoginResponseDto.builder()
                        .accessToken(token.getAccessToken())
                        .refreshToken(token.getRefreshToken())
                        .expireIn(token.getExpiresIn())
                        .refreshExpiresIn(token.getRefreshExpiresIn())
                        .build();
            }
            throw new AppException(ERR_SYSTEM.getCode());
        } catch (FeignException.FeignClientException ex) {
            throw new AppException(ERR_SYSTEM.getCode());
        }
    }

    @Override
    public RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto request) throws AppException {
        try {
            Map<String, Object> headerMap = new HashMap<>();
            headerMap.put(GRANT_TYPE, REFRESH_TOKEN);
            headerMap.put(CLIENT_ID, "chat-app");
            headerMap.put(CLIENT_SECRET, "b8lbjvCS8kpTDm9gQefDY1l45O7qurTH");
            headerMap.put(REFRESH_TOKEN, request.refreshToken());
            ResponseEntity<AccessTokenModel> data = keycloakClient.login(headerMap);
            AccessTokenModel token = data.getBody();
            if (data.getStatusCode().is2xxSuccessful() && token != null) {
                return RefreshTokenResponseDto.builder()
                        .accessToken(token.getAccessToken())
                        .refreshToken(token.getRefreshToken())
                        .expireIn(token.getExpiresIn())
                        .refreshExpiresIn(token.getRefreshExpiresIn())
                        .build();
            }
            throw new AppException(ERR_SYSTEM.getCode());
        } catch (FeignException.FeignClientException ex) {
            throw new AppException(ERR_SYSTEM.getCode());
        }
    }

    @Override
    public String createUser(UserRegisterReqDTO req) throws AppException {
        UsersResource usersResource = keycloak.realm("app-chat").users();

        UserRepresentation user = new UserRepresentation();
        user.setUsername(req.getEmail());
        user.setEmail(req.getEmail());
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setEnabled(true);

        CredentialRepresentation credentials = new CredentialRepresentation();
        credentials.setType("password");
        credentials.setValue(req.getPassword());
        user.setCredentials(Collections.singletonList(credentials));

        Response response = usersResource.create(user);
        if (response.getStatus() == 201) {
            URI location = response.getLocation();
            String path = location.getPath();
            return path.substring(path.lastIndexOf("/") + 1);
        }

        throw new AppException(ERR_SYSTEM.getCode());
    }

    @Override
    public ApiResponse<Boolean> deleteUser(String email) throws AppException {
        List<UserRepresentation> users = keycloak.realm("app-chat")
                .users()
                .search(email);

        if (!users.isEmpty()) {
            String userId = users.getFirst().getId();
            keycloak.realm("app-chat").users().delete(userId);
            return ApiResponse.success(true);
        } else {
            throw new AppException(ERR_SYSTEM.getCode());
        }
    }
}
