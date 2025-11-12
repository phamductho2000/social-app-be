package com.social.chat.service;

import com.social.common.exception.AppException;
import com.social.chat.domain.Conversation;
import com.social.chat.dto.response.ParticipantResDTO;
import com.social.chat.dto.response.UserResponseDTO;

import java.util.List;

public interface ParticipantService {

    List<ParticipantResDTO> saveAll(List<UserResponseDTO> participants, Conversation conversation) throws AppException;

    List<ParticipantResDTO> getParticipantsByConversationId(String conversationId);
}
