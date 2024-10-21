package com.novi.entities;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import jakarta.persistence.GenerationType;


// @MappedSuperclass geeft aan dat 'BaseEntity' geen directe database vertegenwoordigt, maar dat de velden ervan worden geerfd door andere entiteiten die deze class extenden.
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_edited", nullable = false)
    private LocalDateTime lastEdited;

    // Constructors
    public BaseEntity() {
        this.createdDate = LocalDateTime.now();
        this.lastEdited = LocalDateTime.now();
    }

    // Getters and Setters
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

    //PrePersist en PreUpdate hooks om timestamps automatisch te beheren. Deze velden worden automatisch ingesteld wanneer een nieuwe entiteit wordt aangemaakt en opgeslagen.
    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
        this.lastEdited = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.lastEdited = LocalDateTime.now();
    }
}
