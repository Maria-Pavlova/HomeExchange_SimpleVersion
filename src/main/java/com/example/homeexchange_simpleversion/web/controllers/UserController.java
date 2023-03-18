package com.example.homeexchange_simpleversion.web.controllers;

import com.example.homeexchange_simpleversion.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String getUserProfile(Principal principal, Model model) {
        model.addAttribute("userProfile", userService.getUserProfile(principal.getName()));
        return "profile";
    }

    @GetMapping("/all")
    public String getAllUsers( Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "all-users";
    }

}
