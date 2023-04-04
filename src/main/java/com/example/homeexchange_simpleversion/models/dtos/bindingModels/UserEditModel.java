package com.example.homeexchange_simpleversion.models.dtos.bindingModels;

import com.example.homeexchange_simpleversion.models.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
public class UserEditModel implements Serializable {
    private Long id;

    private String firstName;

    private String lastName;

    @NotNull
    private List<Role> roles;

    public UserEditModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserEditModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }


    public UserEditModel setRoles(List<Role> roles) {
        this.roles = roles;
        return this;
    }

    public UserEditModel setId(Long id) {
        this.id = id;
        return this;
    }
}
