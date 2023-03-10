package com.example.homeexchange_simpleversion.models.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class Message extends BaseEntity {

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String subject;

    @ManyToOne
    private User fromUser;

    @ManyToOne
    private User toUser;

    @Column(name = "message_created")
    private LocalDateTime messageCreated;

    @PrePersist
    public void prePersist(){
        setMessageCreated(LocalDateTime.now());
    }

    public String getText() {
        return text;
    }

    public Message setText(String text) {
        this.text = text;
        return this;
    }

    public User getFromUser() {
        return fromUser;
    }

    public Message setFromUser(User fromUser) {
        this.fromUser = fromUser;
        return this;
    }

    public User getToUser() {
        return toUser;
    }

    public Message setToUser(User toUser) {
        this.toUser = toUser;
        return this;
    }

    public LocalDateTime getMessageCreated() {
        return messageCreated;
    }

    public Message setMessageCreated(LocalDateTime messageCreated) {
        this.messageCreated = messageCreated;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public Message setSubject(String subject) {
        this.subject = subject;
        return this;
    }
}

