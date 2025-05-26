package com.social.common.page;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@Builder
public class CustomPage<T> {

    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> extendData;

    public static <T> CustomPage<T> blankPage() {
        return CustomPage.<T>builder()
                .content(List.of())
                .pageNumber(0)
                .pageSize(0)
                .totalElements(0)
                .build();
    }

    public static <P, T> CustomPage<T> buildPage(Page<P> modelPage, List<T> content) {
        return CustomPage.<T>builder()
                .content(content)
                .pageNumber(modelPage.getNumber())
                .pageSize(modelPage.getSize())
                .totalElements(modelPage.getTotalElements())
                .build();
    }

    public static <P, T> CustomPage<T> buildPage(Page<P> modelPage, List<T> content,
                                                 Map<String, Object> extendData) {
        return CustomPage.<T>builder()
                .content(content)
                .extendData(extendData)
                .pageNumber(modelPage.getNumber())
                .pageSize(modelPage.getSize())
                .totalElements(modelPage.getTotalElements())
                .build();
    }

    public static <P> CustomPage<P> buildPage(Page<P> modelPage) {
        return CustomPage.<P>builder()
                .content(modelPage.getContent())
                .pageNumber(modelPage.getNumber())
                .pageSize(modelPage.getSize())
                .totalElements(modelPage.getTotalElements())
                .build();
    }

    public static <P> CustomPage<P> buildPage(Page<P> modelPage, Map<String, Object> extendData) {
        return CustomPage.<P>builder()
                .content(modelPage.getContent())
                .extendData(extendData)
                .pageNumber(modelPage.getNumber())
                .pageSize(modelPage.getSize())
                .totalElements(modelPage.getTotalElements())
                .build();
    }
}