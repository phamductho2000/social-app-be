package com.social.message.service;

import com.social.message.dto.request.ReactionHistoryReqDto;
import com.social.message.exception.ChatServiceException;

public interface ReactionHistoryService {
    void save(ReactionHistoryReqDto request) throws ChatServiceException;
}
