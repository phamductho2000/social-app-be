package com.social.common.dto;

import lombok.Data;

import java.time.Instant;

@Data
public abstract class BaseDTO {
    private String createdBy;
    private String updatedBy;
    private Instant createdAt;
    private Instant updatedAt;
}
