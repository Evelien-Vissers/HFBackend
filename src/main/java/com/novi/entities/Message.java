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
    private Boolean readStatus;

    //Relaties met Profile
    @ManyToOne
    @JoinColumn(name = "senderID")
    private Profile sender;

    @ManyToOne
    @JoinColumn(name = "receiverID")
    private Profile receiver;

    //Relatie met Matching
    @ManyToOne
    @JoinColumn(name = "matchingID")
    private Matching matching;

    // Constructors
    public Message() {
    }

    public Message(String content, LocalDateTime timestamp, Boolean readStatus, Profile sender, Profile receiver) {
        this.content = content;
        this.timestamp = timestamp;
        this.readStatus = readStatus;
        this.sender = sender;
        this.receiver = receiver;

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

    public Boolean getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Boolean readStatus) {
        this.readStatus = readStatus;
    }

    public Profile getSender() {
        return sender;
    }
    public void setSender(Profile sender) {
        this.sender = sender;
    }
    public Profile getReceiver() {
        return receiver;
    }
    public void setReceiver(Profile receiver) {
        this.receiver = receiver;
    }
    public Matching getMatching() {
        return matching;
    }
    public void setMatching(Matching matching) {
        this.matching = matching;
    }
}

