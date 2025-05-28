package com.social.message.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConversationReqDTO {
    private String id;
    private String type;
    private String name;
    private String avatar;
    private String description;
    private List<ParticipantReqDTO> participantInfos;
}
