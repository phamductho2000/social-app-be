package com.social.repo;

import com.social.domain.MessageReadStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageReadStatusRepository extends MongoRepository<MessageReadStatus, String> {

}
