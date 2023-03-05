package com.example.homeexchange_simpleversion.web.controllers;

import com.example.homeexchange_simpleversion.models.dtos.bindingModels.MessageModel;
import com.example.homeexchange_simpleversion.services.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @ModelAttribute("messageModel")
    public MessageModel messageModel(){
        return new MessageModel();
    }


    @GetMapping("/send")
    public String sendMessage(){
        return "contact-form";
    }
}
