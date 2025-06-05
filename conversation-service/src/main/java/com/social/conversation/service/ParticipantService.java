package com.social.conversation.service;

import com.social.common.exception.AppException;
import com.social.conversation.constants.ConversationType;
import com.social.conversation.domain.Conversation;
import com.social.conversation.dto.request.ConversationReqDTO;
import com.social.conversation.dto.response.ParticipantResDTO;
import com.social.conversation.dto.response.UserResponseDTO;
import com.social.conversation.exception.ChatServiceException;

import java.util.List;

public interface ParticipantService {

    List<ParticipantResDTO> saveAll(List<UserResponseDTO> participants, Conversation conversation) throws AppException;

    List<ParticipantResDTO> getParticipantsByConversationId(String conversationId);
}
