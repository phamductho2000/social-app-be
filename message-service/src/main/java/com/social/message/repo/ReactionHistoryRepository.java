package com.social.message.repo;

import com.social.message.domain.ReactionHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReactionHistoryRepository extends MongoRepository<ReactionHistory, String> {

    List<ReactionHistory> findAllByMessageId(String messageId);

    Optional<ReactionHistory> findByMessageIdAndUserId(String messageId, String userId);
}
