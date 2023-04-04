package com.example.homeexchange_simpleversion.web.controllers.rest;

import com.example.homeexchange_simpleversion.models.dtos.viewModels.MessageView;
import com.example.homeexchange_simpleversion.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
public class MessagesRestController {

    private final MessageService messageService;

    public MessagesRestController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/api/messages")
    public ResponseEntity<List<MessageView>> getMessages(Principal principal){
        List<MessageView> messages = messageService.getMessages(principal.getName());
        return ResponseEntity.ok(messages);

    }
}
