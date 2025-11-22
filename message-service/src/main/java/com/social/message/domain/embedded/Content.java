package com.social.message.domain.embedded;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Content {
    private Document document;
    private Video video;
    private Photo photo;
    private Text text;
    private Action action;
}