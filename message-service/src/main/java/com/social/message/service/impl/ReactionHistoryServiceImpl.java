package com.social.message.service.impl;

import com.social.message.domain.ReactionHistory;
import com.social.message.dto.request.ReactionHistoryReqDto;
import com.social.message.repo.ReactionHistoryRepository;
import com.social.message.service.ReactionHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ReactionHistoryServiceImpl implements ReactionHistoryService {

    private final ReactionHistoryRepository reactionHistoryRepository;

    @Override
    public void save(ReactionHistoryReqDto request) {
        Optional<ReactionHistory> exist = reactionHistoryRepository
                .findByMessageIdAndUserId(request.getMessageId(), request.getUserId());

        if (exist.isPresent()) {
            exist.get().setEmoji(request.getEmoji());
            exist.get().setUpdatedAt(Instant.now());
            reactionHistoryRepository.save(exist.get());
        } else {
            ReactionHistory newReaction = ReactionHistory.builder()
                    .userId(request.getUserId())
                    .messageId(request.getMessageId())
                    .emoji(request.getEmoji())
                    .createdAt(Instant.now())
                    .build();
            reactionHistoryRepository.save(newReaction);
        }
    }
}
