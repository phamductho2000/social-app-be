package com.social.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.client.KeycloakClient;
import com.social.client.UserClient;
import com.social.common.dto.ApiResponse;
import com.social.common.exception.AppException;
import com.social.dto.keycloak.request.UserLoginRequestDto;
import com.social.dto.keycloak.request.UserRegisterReqDTO;
import com.social.dto.keycloak.response.UserLoginResponseDto;
import com.social.dto.user.response.UserResponseDTO;
import com.social.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;
import java.util.Objects;

import static com.social.constants.RespCode.ERR_SYSTEM;

@Service
@RequiredArgsConstructor
@SuppressWarnings("unchecked")
public class UserServiceImpl implements UserService {

    private final KeycloakClient keycloakClient;

    private final UserClient userClient;

    @Override
    public UserLoginResponseDto login(UserLoginRequestDto request) throws JsonProcessingException {
        ApiResponse<UserLoginResponseDto> responseDto = keycloakClient.login(request);
        if (!responseDto.isSuccess()) {
            return null;
        }
        String jwt = responseDto.getData().getAccessToken();

        String[] parts = jwt.split("\\.");
        String payloadJson = new String(Base64.getUrlDecoder().decode(parts[1]));

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> payload = mapper.readValue(payloadJson, Map.class);

        String username = (String) payload.get("preferred_username");
        responseDto.getData().setUsername(username);
        return responseDto.getData();
    }

    @Override
    public UserResponseDTO register(UserRegisterReqDTO request) throws AppException {
        if (Objects.isNull(request)) {
            throw new AppException("", ERR_SYSTEM.getCode());
        }
        ApiResponse<Boolean> responseKeycloak = keycloakClient.createUser(request);
        if (!responseKeycloak.isSuccess()) {
            throw new AppException(responseKeycloak.getMessage(), ERR_SYSTEM.getCode());
//            return ApiResponse.error(ERR_SYSTEM, responseKeycloak.getMessage());
        }
        ApiResponse<UserResponseDTO> responseUser = userClient.createUser(request);
        if (!responseUser.isSuccess()) {
            keycloakClient.deleteUser(request.email());
            return ApiResponse.error(ERR_SYSTEM, responseUser.getMessage());
        }
        return ApiResponse.success(responseUser.getData());
    }
}
