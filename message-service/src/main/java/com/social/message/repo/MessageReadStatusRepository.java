package com.social.message.repo;

import com.social.message.domain.MessageReadStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageReadStatusRepository extends MongoRepository<MessageReadStatus, String> {

}
