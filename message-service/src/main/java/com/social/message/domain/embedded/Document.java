package com.social.message.domain.embedded;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    private String id;
    private String fileName;
    private String mimeType;
    private Long size;
    private Instant date;
}