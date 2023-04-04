package com.example.homeexchange_simpleversion.web.controllers;

import com.example.homeexchange_simpleversion.models.dtos.bindingModels.UserEditModel;
import com.example.homeexchange_simpleversion.models.dtos.viewModels.UserProfile;
import com.example.homeexchange_simpleversion.models.enums.Role;
import com.example.homeexchange_simpleversion.services.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public AdminController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping("users/all")
    public String getAllUsers( Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "all-users";
    }


    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        UserProfile profile = userService.getUserProfileById(id);
        UserEditModel userEditModel = modelMapper.map(profile, UserEditModel.class);
        userEditModel.setId(id);

        model.addAttribute("userEditModel", userEditModel);
        model.addAttribute("listRoles", Role.values());
        return "user-edit";
    }


    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @GetMapping("/users/edit/{id}/errors")
    public String editUserErrors(@PathVariable Long id, Model model) {
        model.addAttribute("listRoles", Role.values());
        return "user-edit";
    }


    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @PatchMapping("/users/edit/{id}")
    public String updateHome(@PathVariable Long id,
                             @Valid UserEditModel userEditModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userEditModel", userEditModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userEditModel", bindingResult);
            return "redirect:/users/edit/" + id + "/errors";
        }
        userService.updateUser(userEditModel);
        return "redirect:/users/all";
    }
}
