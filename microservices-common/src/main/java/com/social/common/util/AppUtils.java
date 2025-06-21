package com.social.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import org.springframework.data.domain.ScrollPosition;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

public class AppUtils {

    @Getter
    public static class KeysetScrollPositionDto {
        private Map<String, Object> keys;
        public String direction;
    }

    public static String encodeSearchAfter(ScrollPosition scrollPosition) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            String json = mapper.writeValueAsString(scrollPosition);
            return Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static ScrollPosition decodeSearchAfter(String value) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            String json = new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
            KeysetScrollPositionDto dto = mapper.readValue(json, KeysetScrollPositionDto.class);

            return ScrollPosition.of(
                    dto.keys,
                    ScrollPosition.Direction.valueOf(dto.direction)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
