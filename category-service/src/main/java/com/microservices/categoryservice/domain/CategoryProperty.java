package com.microservices.categoryservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(value="category_property")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryProperty {
    @Id
    private String id;
    private String code;
    private String name;
    private String cTypeCode;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @DBRef
    private Category cTypeId;
    private String type;
}
