package com.social.conversation.service.impl;

import com.social.common.exception.AppException;
import com.social.conversation.constants.ConversationType;
import com.social.conversation.domain.Conversation;
import com.social.conversation.domain.Participant;
import com.social.conversation.dto.request.ConversationReqDTO;
import com.social.conversation.dto.request.ParticipantReqDTO;
import com.social.conversation.dto.response.ParticipantResDTO;
import com.social.conversation.repo.ParticipantsRepository;
import com.social.conversation.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<ParticipantResDTO> saveAll(ConversationReqDTO request, Conversation conversation) throws AppException {
        if (Objects.isNull(request) || Objects.isNull(conversation)) {
            throw new AppException("ParticipantsService: Empty payload", "EMPTY_PAYLOAD");
        }
        if (conversation.getType() == ConversationType.DIRECT) {
            List<ParticipantReqDTO> data = request.getParticipantIds().stream().map(p -> ParticipantReqDTO.builder()
                    .userId(p)
                    .role("MEMBER")
                    .status("ACTIVE")
                    .joinedAt(Instant.now())
                    .build()).toList();

            return participantsRepository.saveAll(data.stream().map(dto -> {
                        Participant participants = mapper.map(dto, Participant.class);
                        participants.setConversationId(new ObjectId(conversation.getId()));
                        return participants;
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
