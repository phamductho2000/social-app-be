package com.social.message.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attachment {
    private String id;
    private String fileName;
    private String originalName;
    private long size;
    private String mimeType;
    private String url;
    private Instant uploadedAt;
}