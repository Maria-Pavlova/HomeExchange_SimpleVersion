package com.example.homeexchange_simpleversion.models.dtos.bindingModels;

import com.example.homeexchange_simpleversion.models.entities.User;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
@NoArgsConstructor
public class MessageModel implements Serializable {
    private String username;
    private String email;
    private String subject;
    private String text;
}
