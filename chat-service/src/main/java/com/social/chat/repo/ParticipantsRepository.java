package com.social.chat.repo;

import com.social.chat.domain.Participant;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantsRepository extends MongoRepository<Participant, String> {
    List<Participant> findAllByConversationId(ObjectId conversationId);
}
