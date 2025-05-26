package com.social.common.page;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class CustomPageScroll<T> {
    private List<T> content;
    private int limit;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> extendData;

    public static <T> CustomPageScroll<T> blankPage() {
        return CustomPageScroll.<T>builder()
                .content(List.of())
                .limit(0)
                .build();
    }

    public static <P, T> CustomPageScroll<T> buildPage(List<T> content, int limit, Map<String, Object> extendData) {
        return CustomPageScroll.<T>builder()
                .content(content)
                .limit(limit)
                .extendData(extendData)
                .build();
    }
}