package com.social.service;

import com.social.dto.response.MessageResDTO;

import java.util.List;

public interface UnreadMessagesService {

    void save(MessageResDTO messageResDTO);

    Boolean deleteUnreadMessages(List<String> messageId);
}
