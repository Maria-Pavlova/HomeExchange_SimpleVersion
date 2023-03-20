package com.example.homeexchange_simpleversion.models.dtos.bindingModels;

import com.example.homeexchange_simpleversion.models.entities.UserRole;
import com.example.homeexchange_simpleversion.models.enums.Role;
import com.example.homeexchange_simpleversion.validation.FieldsMatch;
import com.example.homeexchange_simpleversion.validation.UniqueUserName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Getter
public class UserEditModel implements Serializable {
    private Long id;
    @NotNull
    @Size(min = 2, max = 20)
    private String firstName;
    @NotNull
    @Size(min = 2, max = 20)
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
