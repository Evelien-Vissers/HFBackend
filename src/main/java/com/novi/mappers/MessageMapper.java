package com.novi.mappers;

import com.novi.dtos.MessageInputDTO;
import com.novi.dtos.MessageOutputDTO;
import com.novi.entities.Matching;
import com.novi.entities.Message;
import com.novi.entities.Profile;

import java.time.LocalDateTime;

public class MessageMapper {

    // Map MessageInputDTO naar Message entiteit
    public static Message toMessage(MessageInputDTO messageInputDTO, Profile sender, Profile receiver, Matching matching) {
        Message message = new Message();
        message.setContent(messageInputDTO.getContent());
        message.setTimestamp(LocalDateTime.now());
        message.setReadStatus(false);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setMatching(matching);
        return message;
    }

    // Map Message entiteit naar MessageOutputDTO
    public static MessageOutputDTO toMessageOutputDTO(Message message) {
        return new MessageOutputDTO(
            message.getMessageId(),
            message.getContent(),
            message.getTimestamp(),
            message.getSender().getProfileID(),
            message.getReceiver().getProfileID(),
            message.getReadStatus()
        );
    }
}

