package com.novi.dtos;

import java.time.LocalDateTime;

public class MessageInputDTO {

    private String content;
    private Long receiverId;
    private LocalDateTime timestamp;

    // Constructors
    public MessageInputDTO() {
    }

    public MessageInputDTO(String content, Long receiverId, LocalDateTime timestamp) {
        this.content = content;
        this.receiverId = receiverId;
        this.timestamp = LocalDateTime.now();
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
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

