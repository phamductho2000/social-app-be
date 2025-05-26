package com.social.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by IntelliJ IDEA.
 * Project : documentation-apps
 * User: hendisantika
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 29/10/18
 * Time: 07.55
 * To change this template use File | Settings | File Templates.
 */
@Configuration
public class SwaggerUIConfiguration {

    @Autowired
    private ServiceDefinitionsContext definitionContext;

    @Bean
    public RestTemplate configureTempalte() {
        return new RestTemplate();
    }

//    @Primary
//    @Bean
//    @Lazy
//    public SwaggerResourcesProvider swaggerResourcesProvider(InMemorySwaggerResourcesProvider
//    defaultResourcesProvider, RestTemplate temp) {
//        return () -> {
//            List<SwaggerResource> resources = new ArrayList<>(defaultResourcesProvider.get());
//            resources.clear();
//            resources.addAll(definitionContext.getSwaggerDefinitions());
//            return resources;
//        };
//    }

    @Bean
    public OpenAPI customOpenAPI(@Value("${application-description}") String appDescription, @Value("${application" +
            "-version}") String appVersion) {
        return new OpenAPI()
                .info(new Info()
                        .title("Sample REST API for centalized documentation using Spring Boot and spring-fox swagger" +
                                " 3")
                        .version(appVersion)
                        .description(appDescription)
                        .termsOfService("http://swagger.io/terms/")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));

    }

}

