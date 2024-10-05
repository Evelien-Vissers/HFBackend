package com.novi.entities;
import jakarta.persistence.*;
import org.springframework.data.annotation.Id;
import java.time.LocalDateTime;

@Entity
public class Message extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    private String content;
    private LocalDateTime timestamp;
    private Long senderId;
    private Long receiverId;
    private Boolean readStatus;

    @ManyToOne
    @JoinColumn(name = "profileID")
    private Profile profile;

    // Constructors
    public Message() {
    }

    public Message(String content, LocalDateTime timestamp, Long senderId, Long receiverId, Boolean readStatus, Profile profile) {
        this.content = content;
        this.timestamp = timestamp;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.readStatus = readStatus;
        this.profile = profile;
    }

    // Getters and Setters
    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

    public Boolean getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Boolean readStatus) {
        this.readStatus = readStatus;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
