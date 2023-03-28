package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.models.entities.Amenity;
import com.example.homeexchange_simpleversion.models.entities.Home;
import com.example.homeexchange_simpleversion.models.entities.User;
import com.example.homeexchange_simpleversion.models.entities.UserRole;
import com.example.homeexchange_simpleversion.models.enums.AmenityName;
import com.example.homeexchange_simpleversion.models.enums.HomeType;
import com.example.homeexchange_simpleversion.models.enums.ResidenceType;
import com.example.homeexchange_simpleversion.models.enums.Role;
import com.example.homeexchange_simpleversion.repositories.AmenityRepository;
import com.example.homeexchange_simpleversion.repositories.HomeRepository;
import com.example.homeexchange_simpleversion.repositories.UserRepository;
import com.example.homeexchange_simpleversion.repositories.UserRoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;

@Service
public class InitService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AmenityRepository amenityRepository;
    private final HomeRepository homeRepository;
    private User user;

    @Autowired
    public InitService(UserRoleRepository userRoleRepository, UserRepository userRepository,
                       PasswordEncoder passwordEncoder, AmenityRepository amenityRepository,
                       HomeRepository homeRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.amenityRepository = amenityRepository;
        this.homeRepository = homeRepository;
    }

    @PostConstruct
    public void init() {

        initRoles();
        initUsers();
        initAmenity();
        initHomes();
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
            UserRole userRole = new UserRole().setRole(Role.USER);

            userRoleRepository.save(moderatorRole);
            userRoleRepository.save(adminRole);
            userRoleRepository.save(userRole);
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
        user = new User()
                .setFirstName("User")
                .setLastName("Userov")
                .setUsername("user")
                .setEmail("user@example.com")
                .setPreferredDestinations("Bulgaria, Sofia")
                .setRoles(userRoleRepository.findByRole(Role.USER).orElseThrow())
                .setPassword(passwordEncoder.encode("user"));

        user = userRepository.save(user);
    }

    private void initHomes(){
        if (homeRepository.count() == 0) {
            Home home = new Home();
            home.setTitle("Init Home")
                    .setHomeType(HomeType.APARTMENT)
                    .setResidenceType(ResidenceType.PRIMARY)
                    .setBedrooms(2)
                    .setBathrooms(2)
                    .setPeopleFor(4)
                    .setDescription("Perfect place")
                    .setAvailableFrom(LocalDate.parse("2023-06-10"))
                    .setAvailableTo(LocalDate.parse("2023-06-20"))
                    .setOwner(user)
                    .setTown("Sofia")
                    .setCountry("Bulgaria")
                    .setPublished(false)
                    .setPicture("");
            homeRepository.saveAndFlush(home);
        }
    }
}
