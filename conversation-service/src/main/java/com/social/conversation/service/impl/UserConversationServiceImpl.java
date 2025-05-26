package com.social.conversation.service.impl;

import com.social.conversation.client.UserClient;
import com.social.conversation.domain.Message;
import com.social.conversation.domain.UserConversation;
import com.social.conversation.dto.response.MessageResDTO;
import com.social.conversation.dto.response.UserConversationResDTO;
import com.social.conversation.dto.response.UserResponseDTO;
import com.social.conversation.exception.ChatServiceException;
import com.social.dto.ApiResponse;
import com.social.log.Logger;
import com.social.conversation.repo.UserConversationRepository;
import com.social.conversation.service.ParticipantService;
import com.social.conversation.service.UserConversationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserConversationServiceImpl implements UserConversationService {

    private final ModelMapper mapper;

    private final UserConversationRepository userConversationsRepository;

    private final MongoTemplate mongoTemplate;

    private final ParticipantService participantService;

    private final UserClient userClient;

    private final RedisTemplate<String, Object> redisTemplate;

    private final Logger logger;

    @Override
    public List<UserConversationResDTO> saveAll(List<String> participantIds, String conversationId, String type) throws ChatServiceException {
        log.info("Saving user conversations: {}", participantIds);
        if (null == participantIds || participantIds.isEmpty() || StringUtils.isEmpty(conversationId) || StringUtils.isEmpty(type)) {
            throw new ChatServiceException("UserConversationsService: Empty payload", "EMPTY_PAYLOAD");
        }

        List<UserConversation> data = genDataDirect(participantIds, conversationId);

        return userConversationsRepository.saveAll(data)
                .stream()
                .map(e -> mapper.map(e, UserConversationResDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserConversationResDTO> getUserConversations(String userId, Pageable pageable) {

        MatchOperation matchParticipants = Aggregation.match(Criteria.where("userId").is(userId));

        Aggregation aggregation = Aggregation.newAggregation(
                matchParticipants,
                Aggregation.sort(Sort.by(Sort.Direction.DESC, "updatedAt")),
                Aggregation.skip((long) pageable.getPageNumber() * pageable.getPageSize()),
                Aggregation.limit(pageable.getPageSize())
        );

        AggregationResults<UserConversationResDTO> aggregationResults = mongoTemplate.aggregate(aggregation, "user_conversations", UserConversationResDTO.class);

        List<UserConversationResDTO> results = aggregationResults.getMappedResults();

        long total = mongoTemplate.count(new Query(Criteria.where("userId").is(userId)), "user_conversations");

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public void handleNewMessage(MessageResDTO messageResDTO) throws ChatServiceException {
        if (Objects.isNull(messageResDTO)) {
            throw new ChatServiceException("UserConversationsService: Empty payload", "EMPTY_PAYLOAD");
        }
        String conversationId = messageResDTO.getConversationId();
        Set<Object> userIds = redisTemplate.opsForSet().members(String.format("CONNECT_CONVERSATION:%s", conversationId));
        List<UserConversation> conversations = userConversationsRepository.findAllByConversationId(conversationId);
        List<UserConversation> saves = conversations.stream().peek(conversation -> {
            Message message = Message.builder()
                    .content(messageResDTO.getContent())
                    .senderId(messageResDTO.getSenderId())
                    .timestamp(messageResDTO.getCreatedAt())
                    .build();
            conversation.setLastMessage(message);
            if (!CollectionUtils.isEmpty(userIds)) {
                int count = conversation.getUnreadCount();
                conversation.setUnreadCount(!userIds.contains(messageResDTO.getSenderId()) ? count + 1 : count);
            }
        }).toList();
        userConversationsRepository.saveAll(saves);
    }

    private List<UserConversation> genDataDirect(List<String> participantIds, String conversationId) throws ChatServiceException {
        ApiResponse<List<UserResponseDTO>> response = userClient.getUsersByIds(participantIds);
        if (!response.isSuccess()) {
            throw new ChatServiceException("UserConversationsService: Empty payload", "EMPTY_PAYLOAD");
        }
        List<UserResponseDTO> users = response.getData();
        return participantIds.stream().map(p -> {
            UserResponseDTO recipient = users.stream().filter(f -> !f.getId().equals(p)).findFirst().orElse(null);
            assert recipient != null;
            return UserConversation.builder()
                    .userId(p)
                    .name(recipient.getFullName())
                    .avatar(recipient.getAvatar())
                    .conversationId(conversationId)
                    .type("DIRECT")
                    .unreadCount(0)
                    .build();
        }).toList();
    }
}
