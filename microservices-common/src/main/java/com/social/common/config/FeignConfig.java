package com.social.common.config;

import com.social.common.log.Logger;
import feign.RequestInterceptor;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static com.social.common.constants.CommonConstants.*;

@Configuration
@RequiredArgsConstructor
public class FeignConfig {

    private final Logger logger;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                requestTemplate.header(HEADER_USER_NAME, logger.getUserName());
                requestTemplate.header(HEADER_USER_ID, logger.getUserId());
            }
        };
    }
}
