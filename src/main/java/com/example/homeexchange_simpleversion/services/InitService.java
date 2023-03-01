package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.models.entities.Amenity;
import com.example.homeexchange_simpleversion.models.entities.User;
import com.example.homeexchange_simpleversion.models.entities.UserRole;
import com.example.homeexchange_simpleversion.models.enums.AmenityName;
import com.example.homeexchange_simpleversion.models.enums.Role;
import com.example.homeexchange_simpleversion.repositories.AmenityRepository;
import com.example.homeexchange_simpleversion.repositories.UserRepository;
import com.example.homeexchange_simpleversion.repositories.UserRoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class InitService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AmenityRepository amenityRepository;

    public InitService(UserRoleRepository userRoleRepository, UserRepository userRepository,
                       PasswordEncoder passwordEncoder, AmenityRepository amenityRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.amenityRepository = amenityRepository;
    }

    @PostConstruct
    public void init() {
        initAmenity();
        initRoles();
        initUsers();
    }


    private void initAmenity() {
        if (amenityRepository.count() == 0) {
            Arrays.stream(AmenityName.values())
                    .forEach(amenityName -> {
                        Amenity amenity = new Amenity();
                        amenity.setName(amenityName);
                        amenityRepository.save(amenity);
                    });
        }
    }

    private void initRoles() {
        if (userRoleRepository.count() == 0) {
            UserRole moderatorRole = new UserRole().setRole(Role.MODERATOR);
            UserRole adminRole = new UserRole().setRole(Role.ADMIN);

            userRoleRepository.save(moderatorRole);
            userRoleRepository.save(adminRole);
        }
    }
    private void initUsers() {
        if (userRepository.count() == 0) {
            initAdmin();
            initModerator();
            initUser();
        }
    }

    private void initAdmin() {
        User admin = new User()
                .setFirstName("Admin")
                .setLastName("Adminov")
                .setUsername("admin")
                .setEmail("admin@homeExchange.com")
                .setPassword(passwordEncoder.encode("admin"))
                .setRoles(userRoleRepository.findAll());

        userRepository.save(admin);
    }

    private void initModerator() {
        User moderator = new User()
                .setFirstName("Moderator")
                .setLastName("Moderatorov")
                .setUsername("Moderator")
                .setEmail("moderator@example.com")
                .setPassword(passwordEncoder.encode("moderator"))
                .setRoles(userRoleRepository.findByRole(Role.MODERATOR).orElseThrow());

        userRepository.save(moderator);
    }

    private void initUser() {
        User user = new User()
                .setFirstName("User")
                .setLastName("Userov")
                .setUsername("user")
                .setEmail("user@example.com")
                .setPreferredDestinations("Bulgaria, Sofia")
                .setPassword(passwordEncoder.encode("user"));

        userRepository.save(user);
    }
}
