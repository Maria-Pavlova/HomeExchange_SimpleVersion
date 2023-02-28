package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.repositories.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }


}
