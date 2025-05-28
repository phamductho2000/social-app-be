package com.social.message.service.impl;

import com.social.message.repo.MessageReadStatusRepository;
import com.social.message.repo.MessageRepository;
import com.social.message.service.MessageReadStatusService;
import com.social.message.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class MessageReadStatusServiceImpl implements MessageReadStatusService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private MessageReadStatusRepository messageReadStatusRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageService messageService;

//    @Override
//    public void saveMessageReadStatus(MessageReqDTO req) {
//        log.info("saveMessageReadStatus: {}", req);
//        if (Objects.nonNull(req)) {
//            Set<Object> onlineUserConversations = redisService.getDataSet(String.format("ONLINE_CONVERSATION[%s]", req.getConversationId()));
//            List<MessageReadStatus> entities = onlineUserConversations.stream().map(userId -> MessageReadStatus.builder()
//                    .conversationId(new ObjectId(req.getConversationId()))
//                    .userId((String) userId)
//                    .messageId(new ObjectId(req.getId()))
//                    .readAt(Instant.now())
//                    .build()).toList();
//            messageReadStatusRepository.saveAll(entities);
//        }
//    }
//
//
//    @Override
//    public boolean markAsRead(MessageReadStatusReqDTO req) {
//        log.info("markAsRead: {}", req);
//        if (Objects.nonNull(req)) {
//            List<MessageResDTO> messageResDTOS = messageService.getMessagesUnRead(req.getConversationId(), req.getUserId());
//            if (!messageResDTOS.isEmpty()) {
//                messageReadStatusRepository.saveAll(messageResDTOS.stream().map(message -> MessageReadStatus.builder()
//                        .messageId(new ObjectId(message.getId()))
//                        .conversationId(new ObjectId(req.getConversationId()))
//                        .userId(req.getUserId())
//                        .readAt(Instant.now())
//                        .build()
//                ).toList());
//            }
//        }
//        return true;
//    }
}
