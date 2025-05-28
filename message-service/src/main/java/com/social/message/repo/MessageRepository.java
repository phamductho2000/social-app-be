package com.social.message.repo;

import com.social.message.domain.Message;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {

    @Aggregation(pipeline = {
            "{ $match: { conversationId: ?0 } }",
            "{ $lookup: { " +
                    "    from: 'message_read_status', " +
                    "    let: { messageId: '$_id', conversationId: '$conversationId' }, " +
                    "    pipeline: [ " +
                    "        { $match: { $expr: { $and: [ " +
                    "            { $eq: ['$messageId', '$$messageId'] }, " +
                    "            { $eq: ['$conversationId', '$$conversationId'] }, " +
                    "            { $eq: ['$userId', ?1] } " +
                    "        ] } } } " +
                    "    ], " +
                    "    as: 'readStatus' " +
                    "} }",
            "{ $match: { readStatus: { $size: 0 } } }",
            "{ $project: { readStatus: 0 } }"
    })
    List<Message> findUnreadMessage(ObjectId conversationId, String userId);
}
