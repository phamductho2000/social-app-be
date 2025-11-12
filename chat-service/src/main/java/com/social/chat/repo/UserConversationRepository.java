package com.social.chat.repo;

import com.social.chat.domain.UserConversation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserConversationRepository extends MongoRepository<UserConversation, String> {
    Optional<UserConversation> findByConversationIdAndUserId(String conversationId, String userId);

    List<UserConversation> findAllByConversationIdAndUserIdNot(String conversationId, String userId);

    List<UserConversation> findAllByConversationId(String conversationId);
}
