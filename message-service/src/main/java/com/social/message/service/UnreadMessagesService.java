package com.social.message.service;

import com.social.message.dto.response.MessageResDTO;

import java.util.List;

public interface UnreadMessagesService {

    void save(MessageResDTO messageResDTO);

    Boolean deleteUnreadMessages(List<String> messageId);
}
