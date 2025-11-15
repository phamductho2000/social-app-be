package com.social.message.domain.embedded;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    private String document;
    private String video;
    private String media;
    private String text;
    private String action;
}