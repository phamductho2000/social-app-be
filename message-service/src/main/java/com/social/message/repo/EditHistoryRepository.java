package com.social.message.repo;

import com.social.message.domain.EditHistory;
import com.social.message.domain.MessageHistory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EditHistoryRepository extends MongoRepository<EditHistory, String> {
}
