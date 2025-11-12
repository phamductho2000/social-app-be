package com.social.chat.service.impl;

import com.social.common.exception.AppException;
import com.social.chat.constants.ConversationType;
import com.social.chat.domain.Conversation;
import com.social.chat.domain.Participant;
import com.social.chat.dto.request.ParticipantReqDTO;
import com.social.chat.dto.response.ParticipantResDTO;
import com.social.chat.dto.response.UserResponseDTO;
import com.social.chat.repo.ParticipantsRepository;
import com.social.chat.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

    private final ModelMapper mapper;

    private final ParticipantsRepository participantsRepository;

    @Override
    public List<ParticipantResDTO> saveAll(List<UserResponseDTO> participants, Conversation conversation) throws AppException {
        if (CollectionUtils.isEmpty(participants) || Objects.isNull(conversation)) {
            throw new AppException("ParticipantsService: Empty payload", "EMPTY_PAYLOAD");
        }
        if (conversation.getType() == ConversationType.DIRECT) {
            List<ParticipantReqDTO> data = participants.stream().map(p -> ParticipantReqDTO.builder()
                    .userId(p.getUserId())
                    .username(p.getUsername())
                    .role("MEMBER")
                    .status("ACTIVE")
                    .joinedAt(Instant.now())
                    .build()).toList();

            return participantsRepository.saveAll(data.stream().map(dto -> {
                        Participant entity = mapper.map(dto, Participant.class);
                        entity.setConversationId(new ObjectId(conversation.getId()));
                        return entity;
                    }).collect(Collectors.toList()))
                    .stream().map(entity -> mapper.map(entity, ParticipantResDTO.class))
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<ParticipantResDTO> getParticipantsByConversationId(String conversationId) {
        return participantsRepository.findAllByConversationId(new ObjectId(conversationId))
                .stream().map(entity -> mapper.map(entity, ParticipantResDTO.class))
                .toList();
    }
}
