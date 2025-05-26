package com.social.conversation.dto.response;

import com.social.conversation.dto.request.MessageReqDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConversationResDTO {
    private String id;
    private String type;
    private String name;
    private String avatar;
    private String description;
    private MessageReqDTO lastMessage;
    private List<ParticipantResDTO> participantInfos;
}
