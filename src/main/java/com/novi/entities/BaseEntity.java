package com.novi.entities;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import jakarta.persistence.GenerationType;


@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_edited")
    private LocalDateTime lastEdited;


    public BaseEntity() {
        this.createdDate = LocalDateTime.now();
        this.lastEdited = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
        this.lastEdited = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastEdited = LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(LocalDateTime lastEdited) {
        this.lastEdited = lastEdited;
    }

}
