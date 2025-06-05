package com.social.conversation.service.impl;

import com.social.common.exception.AppException;
import com.social.common.page.CustomPageScroll;
import com.social.common.util.QueryBuilder;
import com.social.conversation.client.UserClient;
import com.social.conversation.constants.ConversationType;
import com.social.conversation.domain.Conversation;
import com.social.conversation.domain.Message;
import com.social.conversation.domain.UserConversation;
import com.social.conversation.dto.request.ConversationReqDTO;
import com.social.conversation.dto.request.MessageReqDTO;
import com.social.conversation.dto.request.SearchConversationRequestDto;
import com.social.conversation.dto.response.MessageResDTO;
import com.social.conversation.dto.response.UserConversationResDTO;
import com.social.conversation.dto.response.UserResponseDTO;
import com.social.conversation.exception.ChatServiceException;
import com.social.common.dto.ApiResponse;
import com.social.common.log.Logger;
import com.social.conversation.repo.UserConversationRepository;
import com.social.conversation.service.ParticipantService;
import com.social.conversation.service.UserConversationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.social.common.util.AppUtils.decodeSearchAfter;
import static com.social.common.util.AppUtils.encodeSearchAfter;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserConversationServiceImpl implements UserConversationService {

    private final ModelMapper mapper;

    private final UserConversationRepository userConversationsRepository;

    private final MongoTemplate mongoTemplate;

    private final UserClient userClient;

    private final RedisTemplate<String, Object> redisTemplate;

    private final Logger logger;

    @Override
    public List<UserConversationResDTO> saveAll(List<UserResponseDTO> participants, Conversation conversation, MessageResDTO messageResDTO) throws AppException {
        log.info("Saving user conversations: {}", participants);
        if (CollectionUtils.isEmpty(participants) || Objects.isNull(conversation)) {
            throw new AppException("UserConversationsService: Empty payload", "EMPTY_PAYLOAD");
        }

        List<UserConversation> data = genDataDirect(participants, conversation, messageResDTO);

        return userConversationsRepository.saveAll(data)
                .stream()
                .map(e -> mapper.map(e, UserConversationResDTO.class))
                .collect(Collectors.toList());
    }

    private List<UserConversation> genDataDirect(List<UserResponseDTO> participants, Conversation conversation, MessageResDTO messageResDTO) throws AppException {

        Message message = mapper.map(messageResDTO, Message.class);

        return participants.stream().map(p -> {
            UserResponseDTO recipient = participants.stream().filter(f -> !f.getUserId().equals(p.getUserId())).findFirst().orElse(null);
            assert recipient != null;
            return UserConversation.builder()
                    .userId(p.getUserId())
                    .username(p.getUsername())
                    .name(recipient.getFullName())
                    .avatar(recipient.getAvatar())
                    .conversationId(conversation.getId())
                    .type(conversation.getType())
                    .unreadCount(0)
                    .lastMessage(message)
                    .build();
        }).toList();
    }

    @Override
    public CustomPageScroll<UserConversationResDTO> search(SearchConversationRequestDto request) throws AppException {

        if (null == request) {
            throw new AppException("Payload empty", "PAYLOAD_EMPTY");
        }

        ScrollPosition scrollPosition = ScrollPosition.keyset();
        Map<String, Object> extendData = new HashMap<>();

        if (StringUtils.isNotEmpty(request.getSearchAfter())) {
            scrollPosition = decodeSearchAfter(request.getSearchAfter());
        }

        Query query = QueryBuilder.builder()
                .regex("fullName", request.getSearchValue())
                .eq("userId", logger.getUserId())
                .build()
                .limit(request.getLimit())
                .with(Sort.by(Sort.Direction.DESC, "createdAt"))
                .with(scrollPosition);

        Window<UserConversation> result = mongoTemplate.scroll(query, UserConversation.class);

        if (result.hasNext()) {
            scrollPosition = result.positionAt(result.size() - 1);
            extendData.put("searchAfter", encodeSearchAfter(scrollPosition));
        }

        return CustomPageScroll.buildPage(result.getContent()
                        .stream().map(e -> mapper.map(e, UserConversationResDTO.class))
                        .toList(),
                result.size(), extendData);
    }

    @Override
    public List<UserConversationResDTO> handleNewMessage(MessageResDTO request) throws ChatServiceException {
        if (Objects.isNull(request)) {
            throw new ChatServiceException("UserConversationsService: Empty payload", "EMPTY_PAYLOAD");
        }
        String conversationId = request.getConversationId();
        List<UserConversation> conversations = userConversationsRepository.findAllByConversationId(conversationId);
        List<UserConversation> saves = conversations.stream().peek(conversation -> {
            Message message = mapper.map(request, Message.class);
            conversation.setLastMessage(message);
            conversation.setUnreadCount(conversation.getUnreadCount() + 1);
        }).toList();
        return userConversationsRepository.saveAll(saves).stream().map(e -> mapper.map(e, UserConversationResDTO.class)).toList();
    }
}
