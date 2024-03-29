package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.models.entities.UserRole;
import com.example.homeexchange_simpleversion.models.enums.Role;
import com.example.homeexchange_simpleversion.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;
    @Autowired
    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public UserRole getByRole(Role role){
       return userRoleRepository.findUserRoleByRole(role).orElse(null);
    }
}
