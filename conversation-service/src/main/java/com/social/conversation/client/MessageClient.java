package com.social.conversation.client;

import com.social.conversation.dto.request.MessageReqDTO;
import com.social.conversation.dto.response.MessageResDTO;
import com.social.common.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "message-service")
public interface MessageClient {

    @PostMapping(value = "/api/message/save", consumes = "application/json")
    ApiResponse<MessageResDTO> saveMessage(@RequestBody MessageReqDTO request);
}