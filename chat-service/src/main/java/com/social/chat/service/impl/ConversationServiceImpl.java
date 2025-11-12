package com.social.chat.service.impl;

import com.social.common.exception.AppException;
import com.social.chat.client.MessageClient;
import com.social.chat.client.UserClient;
import com.social.chat.constants.ConversationType;
import com.social.chat.domain.Conversation;
import com.social.chat.dto.request.ConversationReqDTO;
import com.social.chat.dto.request.MessageReqDTO;
import com.social.chat.dto.response.ConversationResDTO;
import com.social.chat.dto.response.MessageResDTO;
import com.social.chat.dto.response.UserResponseDTO;
import com.social.chat.repo.ConversationRepository;
import com.social.chat.service.ConversationService;
import com.social.chat.service.ParticipantService;
import com.social.chat.service.UserConversationService;
import com.social.common.dto.ApiResponse;
import com.social.common.dto.FilterRequest;
import com.social.common.log.Logger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.social.common.util.SpecificationUtil.createSpecification;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {

    private final ModelMapper mapper;

    private final ConversationRepository conversationRepository;

    private final MongoTemplate mongoTemplate;

    private final ParticipantService participantService;

    private final UserConversationService userConversationService;

    private final MessageClient messageClient;

    private final UserClient userClient;

    private final Logger logger;

    @Override
    public ConversationResDTO saveConversation(ConversationReqDTO request) throws AppException {
        log.info("saveConversation: {}", request);
        if (null == request) {
            throw new AppException("ConversationsService: Empty payload", "EMPTY_PAYLOAD");
        }
        Conversation conversation = mapper.map(request, Conversation.class);
        if (request.getParticipantIds().size() == 1) {
            conversation.setType(ConversationType.DIRECT);
        } else {
            conversation.setType(ConversationType.GROUP);
        }
        conversation = conversationRepository.save(conversation);
        ConversationResDTO result = mapper.map(conversation, ConversationResDTO.class);
        result.setName(request.getName());
        result.setAvatar(request.getAvatar());
        request.getParticipantIds().add(logger.getUserId());
//        participantService.saveAll(request, conversation);
//        userConversationService.saveAll(request, conversation);
        return result;
    }

    @Override
    public Page<ConversationResDTO> getConversations(FilterRequest request) {
//        String currentUsername = SecurityUtils.getCurentUsername();

        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());

        Query query = createSpecification(request.getFilters()).with(pageable);

        long count = mongoTemplate.count(query.skip(-1).limit(-1), Conversation.class);

        List<ConversationResDTO> result = mongoTemplate.find(query, Conversation.class)
                .stream()
                .map(e -> mapper.map(e, ConversationResDTO.class))
                .collect(Collectors.toList());

        return new PageImpl<>(result, pageable, count);
    }

    @Override
    public ConversationResDTO getDetailConversation(String conversationId) {
        return null;
    }

    @Override
    public ConversationResDTO createConversation(ConversationReqDTO request) throws AppException {
        if (Objects.isNull(request)) {
            throw new AppException("ConversationsService: Empty payload", "EMPTY_PAYLOAD");
        }
        Conversation conversation = mapper.map(request, Conversation.class);
        if (request.getParticipantIds().size() == 1) {
            conversation.setType(ConversationType.DIRECT);
        } else {
            conversation.setType(ConversationType.GROUP);
        }
        conversation = conversationRepository.save(conversation);
        request.getParticipantIds().add(logger.getUserId());

        ApiResponse<List<UserResponseDTO>> response = userClient.getUsersByIds(request.getParticipantIds());
        if (!response.isSuccess()) {
            throw new AppException("UserConversationsService: Empty payload", "EMPTY_PAYLOAD");
        }

        List<UserResponseDTO> participants = response.getData();


        participantService.saveAll(participants, conversation);

        MessageReqDTO messageReqDTO = request.getMessage();
        messageReqDTO.setConversationId(conversation.getId());
        messageReqDTO.setSenderId(logger.getUserId());
        ApiResponse<MessageResDTO> responseMsg = messageClient.saveMessage(messageReqDTO);

        if (!response.isSuccess()) {
            throw new AppException("ConversationsService: Empty payload", "EMPTY_PAYLOAD");
        }

        userConversationService.saveAll(participants, conversation, responseMsg.getData());

        return mapper.map(conversation, ConversationResDTO.class);
    }

    public void updatePinMessage(String conversationId, MessageReqDTO request) throws AppException {
        if (Objects.isNull(request)) {

        }
    }
}
