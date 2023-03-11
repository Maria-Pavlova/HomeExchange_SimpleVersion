package com.example.homeexchange_simpleversion.web.controllers;

import com.example.homeexchange_simpleversion.models.dtos.bindingModels.MessageModel;
import com.example.homeexchange_simpleversion.services.MessageService;
import jakarta.validation.Valid;
import org.springframework.boot.Banner;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;

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


    @GetMapping("/send/{toUsername}")
    public String sendMessageForm(@PathVariable String toUsername){
        return "contact-form";
    }

    @PostMapping("/send")
    public String sendMessage(@Valid MessageModel messageModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              @AuthenticationPrincipal UserDetails userDetails
                              //@PathVariable String toUsername
    ){

            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("messageModel", messageModel);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.messageModel", bindingResult);
                return "redirect:/messages/send";
            }
            messageService.sendMessage(messageModel, userDetails);
            return "redirect:/home";
    }

    @GetMapping
    public String getMessages(Principal principal, Model model){
        model.addAttribute("messages", messageService.getMessages(principal.getName()));
        return "messages";

    }
}
