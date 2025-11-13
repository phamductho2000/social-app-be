package com.social.message.repo;

import com.social.message.domain.MessageHistory;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageHistoryRepository extends MongoRepository<MessageHistory, String> {

  Optional<MessageHistory> findFirstByMsgIdAndConversationId(Integer msgId, String conversationId);
}
