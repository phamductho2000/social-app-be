//package com.microservices.apigateway.controller;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.context.ReactiveSecurityContextHolder;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.oauth2.core.oidc.OidcIdToken;
//import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
//import org.springframework.security.oauth2.core.oidc.user.OidcUser;
//import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Mono;
//
//import java.security.Principal;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/user/unau")
//public class AuthController {
//
//    @GetMapping("/get-current-user-login")
//    public String getCurrentUserlogin(Principal principal) {
//        JwtAuthenticationToken jwt  = (JwtAuthenticationToken) principal;
//        Map<String, Object> claims = jwt.getTokenAttributes();
//        String userName = null;
//        if (claims.containsKey("preferred_username")) {
//            userName = String.valueOf(claims.get("preferred_username"));
//        }
//        return userName;
//    }
//
////    @GetMapping("/oidc-principal")
////    public Principal getOidcUserPrincipal(Principal principal) {
////        return principal;
////    }
////
////    @GetMapping("/get-current-user")
////    public Mono<String> getCurrentUser() {
////        return ReactiveSecurityContextHolder.getContext()
////                .flatMap(s -> {
////                    Jwt jwt = (Jwt) s.getAuthentication().getPrincipal();
////                    Map<String, Object> claims = jwt.getClaims();
////                    String userName = null;
////                    if (claims.containsKey("preferred_username")) {
////                        userName = String.valueOf(claims.get("preferred_username"));
////                    }
////                    return Mono.justOrEmpty(userName);
////                });
////    }
//}
