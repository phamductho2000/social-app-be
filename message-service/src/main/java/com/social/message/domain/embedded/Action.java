package com.social.message.domain.embedded;

import com.social.message.constant.MessageActionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Action {
    private MessageActionType type;
}