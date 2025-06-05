package com.social.auth.client;

import com.social.auth.model.AccessTokenModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@FeignClient(name = "keycloak", url = "${keycloak.api-url}")
public interface KeycloakClient {

    @PostMapping(value = "/protocol/openid-connect/token", consumes = "application/x-www-form-urlencoded")
    ResponseEntity<AccessTokenModel> login(Map<String, ?> headers);
}