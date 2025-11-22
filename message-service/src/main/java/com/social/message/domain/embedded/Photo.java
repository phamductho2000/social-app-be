package com.social.message.domain.embedded;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Photo {
    private String id;
    private Instant date;
    private Long size;
    private String mimeType;
    private String fileName;
}