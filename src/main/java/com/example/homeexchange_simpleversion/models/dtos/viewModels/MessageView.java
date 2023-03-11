package com.example.homeexchange_simpleversion.models.dtos.viewModels;

import com.example.homeexchange_simpleversion.models.entities.User;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MessageView implements Serializable {

    private String text;
    private String subject;
    private User fromUser;
    private User toUser;
    private LocalDateTime messageCreated;
}
