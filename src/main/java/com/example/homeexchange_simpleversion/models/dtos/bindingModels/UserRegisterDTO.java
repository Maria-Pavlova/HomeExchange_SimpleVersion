package com.example.homeexchange_simpleversion.models.dtos.bindingModels;

import com.example.homeexchange_simpleversion.validation.FieldsMatch;
import com.example.homeexchange_simpleversion.validation.UniqueUserName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@FieldsMatch(first = "password", second = "confirmPassword", message = "Passwords do not match.")
public class UserRegisterDTO implements Serializable {
    @NotNull
    @Size(min = 2, max = 20)
    private String firstName;
    @NotNull
    @Size(min = 2, max = 20)
    private String lastName;
    @NotNull
    @Size(min = 2, max = 20)
    @UniqueUserName
    private String username;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 2, max = 20)
    private String preferredDestinations;
    @NotNull
    @Size(min = 2, max = 20)
    private String password;
    @NotNull
    @Size(min = 2, max = 20)
    private String confirmPassword;



    public UserRegisterDTO setPreferredDestinations(String preferredDestinations) {
        this.preferredDestinations = preferredDestinations;
        return this;
    }

    public UserRegisterDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserRegisterDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserRegisterDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public UserRegisterDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserRegisterDTO setPassword(String password) {
        this.password = password;
        return this;
    }



    public UserRegisterDTO setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRegisterDTO entity = (UserRegisterDTO) o;
        return Objects.equals(this.firstName, entity.firstName) &&
                Objects.equals(this.lastName, entity.lastName) &&
                Objects.equals(this.username, entity.username) &&
                Objects.equals(this.password, entity.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, username, password);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "firstName = " + firstName + ", " +
                "lastName = " + lastName + ", " +
                "username = " + username + ", " +
                "password = " + password + ")";
    }
}
