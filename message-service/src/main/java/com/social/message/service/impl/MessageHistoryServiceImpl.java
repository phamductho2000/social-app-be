package com.social.message.service.impl;

import static com.social.common.util.AppUtils.decodeSearchAfter;
import static com.social.common.util.AppUtils.encodeSearchAfter;

import com.social.common.log.Logger;
import com.social.common.page.CustomPageScroll;
import com.social.common.util.QueryBuilder;
import com.social.message.constant.MessageStatus;
import com.social.message.domain.MessageHistory;
import com.social.message.dto.EditMessageDto;
import com.social.message.dto.PinMessageDto;
import com.social.message.dto.ReplyMessageDto;
import com.social.message.dto.SendMessageDto;
import com.social.message.dto.request.ReactionHistoryReqDto;
import com.social.message.dto.request.ReactionReqDto;
import com.social.message.dto.request.SearchMessageRequestDto;
import com.social.message.dto.response.MessageResDTO;
import com.social.message.exception.ChatServiceException;
import com.social.message.repo.MessageHistoryRepository;
import com.social.message.service.EditHistoryService;
import com.social.message.service.MessageHistoryService;
import com.social.message.service.ReactionHistoryService;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.util.StringUtils;


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

  private final EditHistoryService editHistoryService;

  @Override
  public MessageResDTO create(SendMessageDto request) throws ChatServiceException {
    log.info("create request: {}", request);
    if (Objects.nonNull(request)) {
      MessageHistory history = modelMapper.map(request, MessageHistory.class);
      history.setConversationId(request.getConversationId());
      history.setStatus(MessageStatus.SENT);
      history.setCreatedAt(Instant.now());
      history.setSentAt(Instant.now());
//            history.setCreatedBy(request.getUserName());
//            history.setUpdatedBy(request.getUserName());
      return modelMapper.map(messageHistoryRepository.save(history), MessageResDTO.class);
    }
    throw new ChatServiceException("MessageService: Empty payload", "EMPTY_PAYLOAD");
  }

  @Override
  public MessageResDTO react(ReactionReqDto request) throws ChatServiceException {
    log.info("react: {}", request);
    if (Objects.nonNull(request) && !StringUtils.hasText(request.messageId())) {
      MessageHistory messageHistory = messageHistoryRepository.findById(request.messageId())
          .orElseThrow(() -> new ChatServiceException("MessageService: not found", "NOT_FOUND"));

      Map<String, Long> reaction = Optional.ofNullable(messageHistory.getSummaryReaction())
          .orElse(new HashMap<>());
      long count;
      if (reaction.containsKey(request.emoji())) {
        count = reaction.get(request.emoji()) + 1;
      } else {
        count = 1;
      }
      reaction.put(request.emoji(), count);
      messageHistory.setSummaryReaction(reaction);

      ReactionHistoryReqDto dto = ReactionHistoryReqDto.builder()
          .messageId(request.messageId())
          .userId(request.userId())
          .emoji(request.emoji())
          .build();
      reactionHistoryService.save(dto);

      return modelMapper.map(messageHistoryRepository.save(messageHistory), MessageResDTO.class);
    }
    throw new ChatServiceException("MessageService: Empty payload", "EMPTY_PAYLOAD");
  }

  @Override
  public MessageResDTO edit(EditMessageDto request) throws ChatServiceException {
    log.info("edit request: {}", request);
    if (Objects.isNull(request) ||
        Objects.isNull(request.getMsgId()) ||
        !StringUtils.hasText(request.getConversationId())) {
      throw new ChatServiceException("MessageService: invalid payload", "INVALID_PAYLOAD");
    }

    MessageHistory exist = messageHistoryRepository.findFirstByMsgIdAndConversationId(
            request.getMsgId(),
            request.getConversationId())
        .orElseThrow(() -> new ChatServiceException("MessageService: not found", "NOT_FOUND"));

    exist.setType(request.getType());
    exist.setContent(request.getContent());
    exist.setAttachments(request.getAttachments());
    exist.setMentions(request.getMentions());
    exist.setEdited(true);
    exist.setUpdatedAt(Instant.now());

    editHistoryService.save(exist, exist.getId());

    return modelMapper.map(messageHistoryRepository.save(exist), MessageResDTO.class);
  }

  @Override
  public MessageResDTO reply(ReplyMessageDto request) throws ChatServiceException {
    log.info("reply request: {}", request);
    if (Objects.isNull(request) ||
        !StringUtils.hasText(request.getClientMsgId()) ||
        !StringUtils.hasText(request.getConversationId())) {
      throw new ChatServiceException("MessageService: invalid payload", "INVALID_PAYLOAD");
    }

    MessageHistory history = modelMapper.map(request, MessageHistory.class);
    history.setConversationId(request.getConversationId());
    history.setStatus(MessageStatus.SENT);
    history.setCreatedAt(Instant.now());
    history.setSentAt(Instant.now());

    return modelMapper.map(messageHistoryRepository.save(history), MessageResDTO.class);
  }

  @Override
  public MessageResDTO pin(PinMessageDto request) throws ChatServiceException {
    log.info("pin request: {}", request);
    if (Objects.isNull(request) || Objects.isNull(request.getMsgId())
        || !StringUtils.hasText(request.getConversationId())
    ) {
      throw new ChatServiceException("MessageService: invalid payload", "INVALID_PAYLOAD");
    }
    MessageHistory exist = messageHistoryRepository.findFirstByMsgIdAndConversationId(
            request.getMsgId(),
            request.getConversationId())
        .orElseThrow(() -> new ChatServiceException("MessageService: not found", "NOT_FOUND"));

    exist.setPinned(true);
    exist.setUpdatedAt(Instant.now());

    messageHistoryRepository.save(exist);

    return MessageResDTO.builder()
        .msgId(request.getMsgId())
        .conversationId(request.getConversationId())
        .isPinned(true)
        .build();
  }

  @Override
  public CustomPageScroll<MessageResDTO> searchMessage(SearchMessageRequestDto request)
      throws ChatServiceException {

    if (null == request) {
      throw new ChatServiceException("Payload empty", "PAYLOAD_EMPTY");
    }

    ScrollPosition scrollPosition = ScrollPosition.keyset();
    Map<String, Object> extendData = new HashMap<>();

    if (StringUtils.hasText(request.getSearchAfter())) {
      scrollPosition = decodeSearchAfter(request.getSearchAfter());
    }

    Query query = QueryBuilder.builder()
        .eq("conversationId", request.getConversationId())
        .build()
        .limit(request.getLimit())
        .with(Sort.by(Sort.Direction.DESC, "createdAt"))
        .with(scrollPosition);

    Window<MessageHistory> result = mongoTemplate.scroll(query, MessageHistory.class);

    Long total = mongoTemplate.count(query, MessageHistory.class);

    if (result.hasNext()) {
      scrollPosition = result.positionAt(result.size() - 1);
      extendData.put("searchAfter", encodeSearchAfter(scrollPosition));
    }

    extendData.put("total", total);

    return CustomPageScroll.buildPage(
        result.getContent().stream().map(e -> modelMapper.map(e, MessageResDTO.class)).toList(),
        result.size(),
        extendData);
  }

  @Override
  public Boolean markReadMessages(List<String> ids) throws ChatServiceException {
    if (CollectionUtils.isEmpty(ids)) {
      throw new ChatServiceException("Payload empty", "PAYLOAD_EMPTY");
    }
    List<MessageResDTO> res = messageHistoryRepository.saveAll(
            messageHistoryRepository.findAllById(ids)
                .stream().peek(p -> p.setStatus(MessageStatus.READ)).toList())
        .stream().map(e -> modelMapper.map(e, MessageResDTO.class)).toList();

//    ObjectMapper objectMapper = new ObjectMapper();
//    objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
//    try {
//      kafkaTemplate.send("MARKED_READ_MESSAGE",
//          objectMapper.writeValueAsString(MarkReadMessageReqDto.builder()
//              .userId(logger.getUserId())
//              .conversationId(res.getFirst().getConversationId())
//              .size(res.size())
//              .build()));
//    } catch (JsonProcessingException ex) {
//      throw new RuntimeException(ex);
//    }
//
//    res.forEach(msg -> {
//      try {
//        kafkaTemplate.send("SENT_MESSAGE",
//            objectMapper.writeValueAsString(MarkReadMessageResDto.builder()
//                .id(msg.getId())
//                .conversationId(msg.getConversationId())
//                .status(msg.getStatus())
//                .build()));
//      } catch (JsonProcessingException e) {
//        throw new RuntimeException(e);
//      }
//    });
    return true;
  }
}
