package com.social.websocket.dto.request;

import com.social.websocket.constant.MessageStatus;
import com.social.websocket.constant.MessageTypeStatus;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record MessageRequestDto(
        String id,
        String tempId,
        String conversationId,
        String senderId,
        String content,
        MessageTypeStatus type,
        MessageStatus status,
        Instant sentAt,
        List<Attachment> attachments,
        List<Reaction> reactions,
        List<Mention> mentions,
        Reply replyTo
) {

    @Builder
    public record Attachment(
            String id,
            String fileName,
            String originalName,
            long size,
            String mimeType,
            String url,
            Instant uploadedAt
    ) {
    }

    @Builder
    public record Reaction(
            String userId,
            String emoji,
            String unified
    ) {
    }

    @Builder
    public record Mention(
            String userId
    ) {
    }

    @Builder
    public record Reply(
            String messageId,
            String senderId,
            String content,
            String contentType,
            List<Attachment> attachments
    ) {
    }

}
