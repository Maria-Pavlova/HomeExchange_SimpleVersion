package com.example.homeexchange_simpleversion.models.dtos.bindingModels;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
public class MessageModel implements Serializable {
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String subject;
    @NotNull
    private String text;
}
