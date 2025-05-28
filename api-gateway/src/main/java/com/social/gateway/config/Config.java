package com.social.gateway.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class Config {
    @Bean
    RouterFunction<ServerResponse> staticResourceLocator(){
//        String path = Paths.get("storage-service/src/uploads/").toAbsolutePath() + "/";
        String publicFilesDir = String.format("%s/uploads/", Paths.get("storage-service/src").toAbsolutePath());

        return RouterFunctions.resources("/uploads/**", new FileSystemResource(publicFilesDir));
    }


}

