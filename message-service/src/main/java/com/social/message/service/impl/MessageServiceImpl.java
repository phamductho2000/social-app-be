package com.social.message.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.common.exception.AppException;
import com.social.common.log.Logger;
import com.social.common.page.CustomPageScroll;
import com.social.common.util.QueryBuilder;
import com.social.message.constant.MessageStatus;
import com.social.message.domain.Message;
import com.social.message.domain.Reaction;
import com.social.message.dto.request.MarkReactionMessageReqDto;
import com.social.message.dto.request.MarkReadMessageReqDto;
import com.social.message.dto.request.MessageReqDTO;
import com.social.message.dto.request.SearchMessageRequestDto;
import com.social.message.dto.response.MessageResDTO;
import com.social.message.exception.ChatServiceException;
import com.social.message.repo.MessageRepository;
import com.social.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Window;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.*;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.social.common.util.AppUtils.decodeSearchAfter;
import static com.social.common.util.AppUtils.encodeSearchAfter;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final ModelMapper modelMapper;

    private final MessageRepository messagesRepository;

    private final MongoTemplate mongoTemplate;

    private final Logger logger;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public MessageResDTO save(MessageReqDTO req) throws ChatServiceException {
        log.info("save: {}", req);
        if (Objects.nonNull(req)) {
            Message chatMessage = modelMapper.map(req, Message.class);
            chatMessage.setConversationId(req.getConversationId());
            chatMessage.setStatus(MessageStatus.SENT);
            chatMessage.setCreatedAt(Instant.now());
            chatMessage.setCreatedBy(req.getUsername());
            chatMessage.setUpdatedAt(Instant.now());
            chatMessage.setUpdatedBy(req.getUsername());
            return modelMapper.map(messagesRepository.save(chatMessage), MessageResDTO.class);
        }
        throw new ChatServiceException("MessageService: Empty payload", "EMPTY_PAYLOAD");
    }

    @Override
    public CustomPageScroll<MessageResDTO> searchMessage(SearchMessageRequestDto request) throws ChatServiceException {

        if (null == request) {
            throw new ChatServiceException("Payload empty", "PAYLOAD_EMPTY");
        }

        ScrollPosition scrollPosition = ScrollPosition.keyset();
        Map<String, Object> extendData = new HashMap<>();

        if (StringUtils.isNotEmpty(request.getSearchAfter())) {
            scrollPosition = decodeSearchAfter(request.getSearchAfter());
        }

        Query query = QueryBuilder.builder()
                .eq("conversationId", request.getConversationId())
                .build()
                .limit(request.getLimit())
                .with(Sort.by(Sort.Direction.DESC, "createdAt"))
                .with(scrollPosition);

        Window<Message> result = mongoTemplate.scroll(query, Message.class);

        if (result.hasNext()) {
            scrollPosition = result.positionAt(result.size() - 1);
            extendData.put("searchAfter", encodeSearchAfter(scrollPosition));
        }

        return CustomPageScroll.buildPage(result.getContent().stream().map(e -> modelMapper.map(e, MessageResDTO.class)).toList(),
                result.size(),
                extendData);
    }

    @Override
    public Boolean markReadMessages(List<String> ids) throws ChatServiceException {
        if (CollectionUtils.isEmpty(ids)) {
            throw new ChatServiceException("Payload empty", "PAYLOAD_EMPTY");
        }
        List<MessageResDTO> res = messagesRepository.saveAll(messagesRepository.findAllById(ids)
                        .stream().peek(p -> p.setStatus(MessageStatus.SEEN)).toList())
                .stream().map(e -> modelMapper.map(e, MessageResDTO.class)).toList();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            kafkaTemplate.send("MARK_READ_MESSAGE_SUCCESS", objectMapper.writeValueAsString(MarkReadMessageReqDto.builder()
                    .userId(logger.getUserId())
                    .conversationId(res.getFirst().getConversationId())
                    .size(res.size())
                    .build()));
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
        return true;
    }

    @Override
    public Boolean markReaction(MarkReactionMessageReqDto request) throws AppException {
        Message message = messagesRepository.findById(request.getMessageId())
                .orElseThrow(() -> new AppException("NOT_FOUND"));

        String userId = logger.getUserId();
        String emoji = request.getEmoji();
        String unified = request.getUnified();

        Reaction newReaction = Reaction.builder()
                .userId(userId)
                .username(logger.getUserName())
                .fullName(request.getFullName())
                .emoji(emoji)
                .unified(unified)
                .build();

        List<Reaction> reactions = new ArrayList<>(Optional.ofNullable(message.getReactions())
                .orElseGet(ArrayList::new));

        Optional<Reaction> existing = reactions.stream()
                .filter(r -> r.getUserId().equals(userId))
                .findFirst();

        if (existing.isPresent()) {
            Reaction reaction = existing.get();
            reaction.setEmoji(emoji);
            reaction.setUnified(unified);
        } else {
            reactions.add(newReaction);
        }

        message.setReactions(reactions);
        messagesRepository.save(message);

        return true;
    }
}
