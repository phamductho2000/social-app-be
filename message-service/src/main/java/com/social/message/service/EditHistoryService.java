package com.social.message.service;

import com.social.message.domain.MessageHistory;
import com.social.message.exception.ChatServiceException;

public interface EditHistoryService {
    void save(MessageHistory request, String refId) throws ChatServiceException;
}
