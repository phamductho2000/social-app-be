package com.social.auth.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.auth.client.UserClient;
import com.social.auth.dto.keycloak.request.UserLoginRequestDto;
import com.social.auth.dto.keycloak.request.UserRegisterReqDTO;
import com.social.auth.dto.keycloak.response.UserLoginResponseDto;
import com.social.auth.dto.user.response.UserResponseDTO;
import com.social.auth.service.KeycloakService;
import com.social.auth.service.UserService;
import com.social.common.dto.ApiResponse;
import com.social.common.exception.AppException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;
import java.util.Objects;

import static com.social.auth.constants.RespCode.ERR_SYSTEM;

@Service
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class UserServiceImpl implements UserService {

    private final KeycloakService keycloakService;

    private final UserClient userClient;

    @Override
    public UserLoginResponseDto login(UserLoginRequestDto request) throws JsonProcessingException, AppException {
        UserLoginResponseDto responseDto = keycloakService.login(request);

        String jwt = responseDto.getAccessToken();

        String[] parts = jwt.split("\\.");
        String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> payload = mapper.readValue(payloadJson, Map.class);

        String username = (String) payload.get("preferred_username");
        responseDto.setUsername(username);
        return responseDto;
    }

    @Override
    public UserResponseDTO register(UserRegisterReqDTO request) throws AppException {
        if (Objects.isNull(request)) {
            throw new AppException("", ERR_SYSTEM.getCode());
        }
        String subId = keycloakService.createUser(request);
        request.setUserId(subId);
        ApiResponse<UserResponseDTO> responseUser = userClient.createUser(request);
        if (!responseUser.isSuccess()) {
            keycloakService.deleteUser(request.getEmail());
            throw new AppException(ERR_SYSTEM.getCode(), responseUser.getMessage());
        }
        return responseUser.getData();
    }
}
