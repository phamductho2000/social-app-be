package com.social.chat.domain;

import com.social.chat.constants.ConversationType;
import com.social.common.domain.BaseDomain;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(value="conversation")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Conversation extends BaseDomain {
    @Id
    private String id;
    private ConversationType type;
    private String name;
    private String avatar;
    private String description;
}
