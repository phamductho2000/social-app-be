package com.social.chat.client;

import com.social.common.config.FeignConfig;
import com.social.chat.dto.request.MessageReqDTO;
import com.social.chat.dto.response.MessageResDTO;
import com.social.common.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "message-service", configuration = FeignConfig.class)
public interface MessageClient {

    @PostMapping(value = "/api/message/save", consumes = "application/json")
    ApiResponse<MessageResDTO> saveMessage(@RequestBody MessageReqDTO request);
}