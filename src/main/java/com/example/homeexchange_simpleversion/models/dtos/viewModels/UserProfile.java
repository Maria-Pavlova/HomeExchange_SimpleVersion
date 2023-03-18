package com.example.homeexchange_simpleversion.models.dtos.viewModels;

import com.example.homeexchange_simpleversion.models.entities.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile implements Serializable {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String preferredDestinations;
    private List<UserRole> roles;


}
