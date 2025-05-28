package com.social.message.service.impl;

import com.social.common.log.Logger;
import com.social.common.page.CustomPageScroll;
import com.social.common.util.QueryBuilder;
import com.social.message.constant.MessageStatus;
import com.social.message.domain.Message;
import com.social.message.dto.request.MessageReqDTO;
import com.social.message.dto.request.SearchMessageRequestDto;
import com.social.message.dto.response.MessageResDTO;
import com.social.message.exception.ChatServiceException;
import com.social.message.repo.MessageRepository;
import com.social.message.service.MessageService;
import com.social.message.service.UnreadMessagesService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    private final UnreadMessagesService unreadMessagesService;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final Logger logger;

    @Override
    public MessageResDTO save(MessageReqDTO req) throws ChatServiceException {
        log.info("save: {}", req);
        if (Objects.nonNull(req)) {
            Message chatMessage = modelMapper.map(req, Message.class);
            chatMessage.setConversationId(req.getConversationId());
            chatMessage.setSenderId(logger.getUserId());
            return modelMapper.map(messagesRepository.save(chatMessage), MessageResDTO.class);
        }
        throw new ChatServiceException("MessageService: Empty payload", "EMPTY_PAYLOAD");
    }

    @Override
    public CustomPageScroll<MessageResDTO> getScrollMessages(SearchMessageRequestDto request) throws ChatServiceException {

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

        Window<MessageResDTO> result = mongoTemplate.scroll(query, MessageResDTO.class);

        if (result.hasNext()) {
            scrollPosition = result.positionAt(result.size() - 1);
            extendData.put("searchAfter", encodeSearchAfter(scrollPosition));
        }

        return CustomPageScroll.buildPage(result.getContent(), result.size(), extendData);
    }

    @Override
    public Boolean markReadMessages(List<String> ids) throws ChatServiceException {
        if (CollectionUtils.isEmpty(ids)) {
            throw new ChatServiceException("Payload empty", "PAYLOAD_EMPTY");
        }

        messagesRepository.saveAll(messagesRepository.findAllById(ids)
                .stream()
                .peek(p -> p.setStatus(MessageStatus.READ))
                .toList());
        return true;
    }
}
