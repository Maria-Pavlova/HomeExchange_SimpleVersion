package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.models.entities.UserRole;
import com.example.homeexchange_simpleversion.models.enums.Role;
import com.example.homeexchange_simpleversion.repositories.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public UserRole getByRole(Role role){
       return userRoleRepository.findUserRoleByRole(role).orElseThrow();
    }
}
