package com.example.homeexchange_simpleversion.models.entities;

import com.example.homeexchange_simpleversion.models.enums.Role;
import jakarta.persistence.*;

@Entity
@Table(name = "rols")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Role role;


    public Long getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public UserRole setId(Long id) {
        this.id = id;
        return this;
    }

    public UserRole setRole(Role role) {
        this.role = role;
        return this;
    }
}
