package com.example.homeexchange_simpleversion.web.controllers;

import com.example.homeexchange_simpleversion.models.dtos.bindingModels.MessageModel;
import com.example.homeexchange_simpleversion.services.MessageService;
import com.example.homeexchange_simpleversion.services.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;


@Controller
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @ModelAttribute("messageModel")
    public MessageModel messageModel(){
        return new MessageModel();
    }


    @GetMapping("/send/{username}")
    public String sendMessageForm(@PathVariable String username, Model model){
        model.addAttribute("toUser", userService.findByUsername(username).get());
        return "contact-form";
    }


    @PostMapping("/send/{username}")
    public String sendMessage( @PathVariable String username,
                              @Valid MessageModel messageModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              @AuthenticationPrincipal UserDetails userDetails){

            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("messageModel", messageModel);
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.messageModel", bindingResult);
                return "redirect:/messages/send";
            }
            messageService.sendMessage(messageModel, userDetails, username);
            return "redirect:/home";
    }

    @GetMapping
    public String getMessages(Principal principal, Model model){
        model.addAttribute("messages", messageService.getMessages(principal.getName()));
        return "messages";
    }
}
