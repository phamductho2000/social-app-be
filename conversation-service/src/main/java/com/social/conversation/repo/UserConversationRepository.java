package com.social.conversation.repo;

import com.social.conversation.domain.UserConversation;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserConversationRepository extends MongoRepository<UserConversation, String> {
    Optional<UserConversation> findByConversationIdAndUserId(ObjectId conversationId, String userId);

    List<UserConversation> findAllByConversationIdAndUserIdNot(String conversationId, String userId);
}
