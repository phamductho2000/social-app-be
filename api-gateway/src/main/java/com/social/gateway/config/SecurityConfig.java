//package com.microservices.apigateway.config;
//
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.savedrequest.NullRequestCache;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.security.web.server.savedrequest.ServerRequestCache;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.reactive.CorsConfigurationSource;
//import org.springframework.web.cors.reactive.CorsWebFilter;
//import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.RouterFunctions;
//import reactor.core.publisher.Mono;
//
//import java.nio.file.Paths;
//import java.util.Arrays;
//import java.util.List;
//
//@Configuration
//@EnableWebFluxSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
//        return httpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable)
//                .authorizeExchange(exchange -> exchange.pathMatchers(
//                                "/eureka/**",
//                                "/api/user/unau/**",
//                                "/uploads/**",
//                                "/api/public/**",
//                                "/webjars/swagger-ui/**",
//                                "/swagger-ui/**",
//                                "/swagger-ui.html",
//                                "/v3/api-docs/**",
//                                "/api/storage/get/**",
//                                "/chat-websocket/**",
//                                "/ws/**")
//                        .permitAll()
//                        .anyExchange().authenticated()
//                ).oauth2ResourceServer((oauth) -> oauth.jwt(Customizer.withDefaults()))
//                .build();
//    }
//
//    @Bean
//    public CorsWebFilter corsFilter() {
//        return new CorsWebFilter(corsConfigurationSource());
//    }
//
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowedOrigins(List.of("http://localhost:8000")); // Địa chỉ frontend của bạn
//        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        config.setAllowedHeaders(List.of("*"));
//        config.setAllowCredentials(true); // Quan trọng: cho phép credentials
//        config.setMaxAge(3600L); // Tùy chọn: đặt thời gian cache cho preflight request
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }
//
////    @Bean
////    public GlobalFilter customGlobalFilter() {
////
////        return ((exchange, chain) -> exchange.getPrincipal().map(principal -> {
////            String userName = "";
////
////            if (principal != null) {
////                //Get username from Principal
////                userName = principal.getName();
////            }
////            // adds header to proxied request
////            exchange.getRequest().mutate()
////                    .header("X-Auth-Id", userName)
////                    .build();
////            return exchange;
////        }).flatMap(chain::filter).then(Mono.fromRunnable(() -> {
////
////        })));
////    }
//}
