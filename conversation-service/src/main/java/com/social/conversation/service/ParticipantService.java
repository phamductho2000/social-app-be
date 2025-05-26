package com.social.conversation.service;

import com.social.conversation.dto.response.ParticipantResDTO;
import com.social.conversation.exception.ChatServiceException;

import java.util.List;

public interface ParticipantService {

    List<ParticipantResDTO> saveAll(List<String> participantIds, String conversationId, String type) throws ChatServiceException;

    List<ParticipantResDTO> getParticipantsByConversationId(String conversationId);
}
