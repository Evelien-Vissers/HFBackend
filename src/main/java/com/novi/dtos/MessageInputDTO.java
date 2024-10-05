package com.novi.dtos;

import java.time.LocalDateTime;

public class MessageInputDTO {

    private String content;
    private Long receiverId;

    // Constructors
    public MessageInputDTO() {
    }

    public MessageInputDTO(String content, Long receiverId) {
        this.content = content;
        this.receiverId = receiverId;
    }

    // Getters and Setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }
}

