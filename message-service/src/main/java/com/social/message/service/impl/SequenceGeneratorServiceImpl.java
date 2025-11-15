package com.social.message.service.impl;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import com.social.message.domain.SequenceGenerator;
import com.social.message.service.SequenceGeneratorService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SequenceGeneratorServiceImpl implements SequenceGeneratorService {

  private final MongoOperations mongoOperations;

  @Override
  public synchronized int generateMsgId(String conversationId) {
    SequenceGenerator counter = mongoOperations.findAndModify(query(where("conversationId").is(conversationId)),
        new Update().inc("sequence", 1), options().returnNew(true).upsert(true),
        SequenceGenerator.class);
    if (Objects.isNull(counter)) {
      mongoOperations.insert(
          SequenceGenerator.builder().conversationId(conversationId).sequence(1).build());
      return 1;
    }
    return counter.getSequence();
  }
}
