package com.example.homeexchange_simpleversion.web.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PagesController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetails userDetails) {
        return "index";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
    @GetMapping("/pages/all")
    public String all() {
        return "all";
    }

    @GetMapping("/pages/moderator")
    public String moderator() {
        return "moderator";
    }

    @GetMapping("/pages/admin")
    public String admin() {
        return "admin";
    }
}
