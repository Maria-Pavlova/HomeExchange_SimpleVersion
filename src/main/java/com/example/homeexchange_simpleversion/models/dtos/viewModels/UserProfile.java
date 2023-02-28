package com.example.homeexchange_simpleversion.models.dtos.viewModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile implements Serializable {
    private String firstName;
    private String lastName;
    private String preferredDestinations;

    @Override
    public String toString() {
        return "UserProfile{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", preferredDestinations='" + preferredDestinations + '\'' +
                '}';
    }
}
