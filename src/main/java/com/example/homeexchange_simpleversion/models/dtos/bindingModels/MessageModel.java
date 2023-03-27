package com.example.homeexchange_simpleversion.models.dtos.bindingModels;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@NoArgsConstructor
public class MessageModel implements Serializable {
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String subject;
    @NotNull
    @Size(min = 5)
    private String text;

    public MessageModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public MessageModel setEmail(String email) {
        this.email = email;
        return this;
    }

    public MessageModel setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public MessageModel setText(String text) {
        this.text = text;
        return this;
    }
}
