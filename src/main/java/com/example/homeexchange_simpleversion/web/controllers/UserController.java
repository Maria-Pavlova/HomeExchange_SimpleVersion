package com.example.homeexchange_simpleversion.web.controllers;

import com.example.homeexchange_simpleversion.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile/{username}")
    public String getUserProfile(@PathVariable String username, Model model){
       model.addAttribute("userProfile", userService.getUserProfile(username));
       return "profile";
    }

}
