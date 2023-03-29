package com.example.homeexchange_simpleversion.models.dtos.viewModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageView implements Serializable {

    private String text;
    private String subject;
    private String fromUserUsername;
    private String fromUserEmail;
    private LocalDateTime messageCreated;
}
