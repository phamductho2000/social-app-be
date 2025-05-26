package com.social.conversation.domain;

import com.social.conversation.constants.ConversationType;
import com.social.domain.BaseDomain;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document(value="conversations")
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
