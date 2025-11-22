package com.social.message.domain.embedded;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    private String id;
    private Double duration;
    private String fileName;
    private String mediaType;
    private String mimeType;
    private Long size;
    private Integer width;
    private Integer height;
    private Instant date;
}