package com.novi.mappers;

import com.novi.dtos.MessageInputDTO;
import com.novi.dtos.MessageOutputDTO;
import com.novi.entities.Message;

public class MessageMapper {

    // Map MessageInputDTO naar Message entiteit
    public static Message toMessage(MessageInputDTO messageInputDTO, Long matchId) {
        Message message = new Message();
        message.setMatchId(matchId);
        message.setSenderId(messageInputDTO.getSenderId());
        message.setReceiverId(messageInputDTO.getReceiverId());
        message.setContent(messageInputDTO.getContent());
        message.setTimestamp(messageInputDTO.getTimestamp()); // Of stel het hier in met LocalDateTime.now()
        return message;
    }

    // Map Message entiteit naar MessageOutputDTO
    public static MessageOutputDTO toMessageOutputDTO(Message message) {
        MessageOutputDTO dto = new MessageOutputDTO();
        dto.setId(message.getId());
        dto.setMatchId(message.getMatchId());
        dto.setSenderId(message.getSenderProfile().getId());
        dto.setReceiverId(message.getReceiverProfile().getId());
        dto.setContent(message.getContent());
        dto.setTimestamp(message.getTimestamp());
        dto.setReadStatus(message.isReadStatus());
        return dto;
    }
}

