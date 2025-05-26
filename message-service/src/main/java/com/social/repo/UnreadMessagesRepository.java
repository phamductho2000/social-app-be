package com.social.repo;

import com.social.domain.UnreadMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnreadMessagesRepository extends MongoRepository<UnreadMessage, String> {

}
