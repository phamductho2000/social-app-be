package com.social.conversation.service.impl;

import com.social.conversation.domain.Participant;
import com.social.conversation.dto.request.ParticipantReqDTO;
import com.social.conversation.dto.response.ParticipantResDTO;
import com.social.conversation.exception.ChatServiceException;
import com.social.conversation.repo.ParticipantsRepository;
import com.social.conversation.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

    private final ModelMapper mapper;

    private final ParticipantsRepository participantsRepository;

    @Override
    public List<ParticipantResDTO> saveAll(List<String> participantIds, String conversationId, String type) throws ChatServiceException {
        log.info("Saving participants: {}", participantIds);
        if (null == participantIds || participantIds.isEmpty() || StringUtils.isEmpty(conversationId) || StringUtils.isEmpty(type)) {
            throw new ChatServiceException("ParticipantsService: Empty payload", "EMPTY_PAYLOAD");
        }
//        if (type.equals("DIRECT")) {
        List<ParticipantReqDTO> data = participantIds.stream().map(p -> ParticipantReqDTO.builder()
                .userId(p)
                .role("MEMBER")
                .status("ACTIVE")
                .joinedAt(Instant.now())
                .build()).toList();
//        }
        return participantsRepository.saveAll(data.stream().map(dto -> {
                    Participant participants = mapper.map(dto, Participant.class);
                    participants.setConversationId(new ObjectId(conversationId));
                    return participants;
                }).collect(Collectors.toList()))
                .stream().map(entity -> mapper.map(entity, ParticipantResDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ParticipantResDTO> getParticipantsByConversationId(String conversationId) {
        return participantsRepository.findAllByConversationId(new ObjectId(conversationId))
                .stream().map(entity -> mapper.map(entity, ParticipantResDTO.class))
                .toList();
    }
}
