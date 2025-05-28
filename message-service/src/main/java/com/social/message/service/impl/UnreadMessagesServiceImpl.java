package com.social.message.service.impl;

import com.social.message.domain.UnreadMessage;
import com.social.message.dto.response.MessageResDTO;
import com.social.message.repo.UnreadMessagesRepository;
import com.social.message.service.UnreadMessagesService;
import org.bson.types.ObjectId;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UnreadMessagesServiceImpl implements UnreadMessagesService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UnreadMessagesRepository unreadMessagesRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void save(MessageResDTO messageResDTO) {
        UnreadMessage entity = UnreadMessage.builder().build();
        entity.setConversationId(new ObjectId(messageResDTO.getConversationId()));
//        return modelMapper.map(, UnreadMessagesResDTO.class);
        unreadMessagesRepository.save(entity);
    }

    @Override
    public Boolean deleteUnreadMessages(List<String> messageIds) {
        unreadMessagesRepository.deleteAllById(messageIds);
        return true;
    }

}
