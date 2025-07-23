package com.social.message.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.common.log.Logger;
import com.social.common.page.CustomPageScroll;
import com.social.common.util.QueryBuilder;
import com.social.message.constant.MessageStatus;
import com.social.message.domain.MessageHistory;
import com.social.message.domain.ReactionHistory;
import com.social.message.dto.request.MarkReadMessageReqDto;
import com.social.message.dto.request.MessageReqDTO;
import com.social.message.dto.request.ReactionHistoryReqDto;
import com.social.message.dto.request.ReactionReqDto;
import com.social.message.dto.request.SearchMessageRequestDto;
import com.social.message.dto.response.MarkReadMessageResDto;
import com.social.message.dto.response.MessageResDTO;
import com.social.message.exception.ChatServiceException;
import com.social.message.repo.MessageHistoryRepository;
import com.social.message.service.MessageHistoryService;
import com.social.message.service.ReactionHistoryService;
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
public class MessageHistoryServiceImpl implements MessageHistoryService {

    private final ModelMapper modelMapper;

    private final MessageHistoryRepository messageHistoryRepository;

    private final MongoTemplate mongoTemplate;

    private final Logger logger;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final ReactionHistoryService reactionHistoryService;

    @Override
    public MessageResDTO save(MessageReqDTO request) throws ChatServiceException {
        log.info("save: {}", request);
        if (Objects.nonNull(request)) {
            MessageHistory chatMessageHistory = modelMapper.map(request, MessageHistory.class);
            chatMessageHistory.setConversationId(request.getConversationId());
            chatMessageHistory.setStatus(MessageStatus.SENT);
            chatMessageHistory.setCreatedAt(Instant.now());
            chatMessageHistory.setCreatedBy(request.getUserName());
            chatMessageHistory.setUpdatedAt(Instant.now());
            chatMessageHistory.setUpdatedBy(request.getUserName());
            return modelMapper.map(messageHistoryRepository.save(chatMessageHistory), MessageResDTO.class);
        }
        throw new ChatServiceException("MessageService: Empty payload", "EMPTY_PAYLOAD");
    }

  @Override
  public MessageResDTO react(ReactionReqDto request) throws ChatServiceException {
    log.info("react: {}", request);
    if (Objects.nonNull(request) && StringUtils.isNotEmpty(request.messageId())) {
      MessageHistory messageHistory = messageHistoryRepository.findById(request.messageId())
          .orElseThrow(() -> new ChatServiceException("MessageService: not found", "NOT_FOUND"));

      Map<String, Long> reaction = messageHistory.getSummaryReaction();
      long count;
      if (reaction.containsKey(request.emoji())) {
        count = reaction.get(request.emoji()) + 1;
      } else {
        count = 1;
      }
      reaction.put(request.emoji(), count);

      ReactionHistoryReqDto dto =modelMapper.map(messageHistory, ReactionHistoryReqDto.class);

      reactionHistoryService.save(dto);

      return modelMapper.map(messageHistoryRepository.save(messageHistory), MessageResDTO.class);
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

        Window<MessageHistory> result = mongoTemplate.scroll(query, MessageHistory.class);

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
        List<MessageResDTO> res = messageHistoryRepository.saveAll(messageHistoryRepository.findAllById(ids)
                        .stream().peek(p -> p.setStatus(MessageStatus.READ)).toList())
                .stream().map(e -> modelMapper.map(e, MessageResDTO.class)).toList();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            kafkaTemplate.send("MARKED_READ_MESSAGE", objectMapper.writeValueAsString(MarkReadMessageReqDto.builder()
                    .userId(logger.getUserId())
                    .conversationId(res.getFirst().getConversationId())
                    .size(res.size())
                    .build()));
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }

        res.forEach(msg -> {
            try {
                kafkaTemplate.send("SENT_MESSAGE", objectMapper.writeValueAsString(MarkReadMessageResDto.builder()
                        .id(msg.getId())
                        .conversationId(msg.getConversationId())
                        .status(msg.getStatus())
                        .build()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
        return true;
    }
}
