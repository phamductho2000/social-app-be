package com.social.chat.dto.response;

import com.social.chat.constants.ConversationType;
import com.social.chat.dto.request.MessageReqDTO;
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
    private ConversationType type;
    private String name;
    private String avatar;
    private String description;
    private MessageReqDTO lastMessage;
    private List<ParticipantResDTO> participantInfos;
}
