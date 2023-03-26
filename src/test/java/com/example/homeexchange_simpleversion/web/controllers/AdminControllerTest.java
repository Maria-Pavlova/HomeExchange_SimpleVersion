package com.example.homeexchange_simpleversion.web.controllers;

import com.example.homeexchange_simpleversion.models.dtos.bindingModels.UserEditModel;
import com.example.homeexchange_simpleversion.models.entities.User;
import com.example.homeexchange_simpleversion.models.entities.UserRole;
import com.example.homeexchange_simpleversion.models.enums.Role;
import com.example.homeexchange_simpleversion.repositories.UserRepository;
import com.example.homeexchange_simpleversion.repositories.UserRoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest {
    public static final String F_NAME = "FirstUser1";
    public static final String L_NAME = "LastUser1";
    public static final String USER_NAME = "TestUser1";
    public static final String EMAIL = "User1@mail.com";
    public static final String PASS = "user1";

    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private UserRepository mockUserRepository;

    @Autowired
    private MockMvc mockMvc;


  //  private User testUser;



    @BeforeEach
    void SetUp() {
//
//        UserRole roleUser = new UserRole().setRole(Role.USER);
//        userRoleRepository.save(roleUser);
//        testUser = new User();
//        testUser
//                .setUsername("IvanForTest")
//                .setPassword("test")
//                .setFirstName("Ivan")
//                .setLastName("Ivanov")
//                .setEmail("ivan@com")
//                .setRoles(List.of(roleUser));
//        testUser = userRepository.save(testUser);
    }

    @AfterEach
    void tearDown() {
        mockUserRepository.deleteAll();
        userRoleRepository.deleteAll();
    }

    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    @Test
    void testGetAllUsers() throws Exception {
        initUsers();
        mockMvc.perform(get("/users/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("all-users"))
                .andExpect(model().attributeExists("users"));
        Optional<User> byUsername = mockUserRepository.findByUsername(USER_NAME);

        Assertions.assertTrue(byUsername.isPresent());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void testGetUserById() throws Exception {
        User user = initUsers();
        long id = user.getId();
        mockMvc.perform(get("/users/edit/" + id))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userEditModel"))
                .andExpect(model().attributeExists("listRoles"))
                .andExpect(view().name("user-edit"));
    }

//    @Test
//    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
//    void testUpdateUserById() throws Exception {
//           User user1 = initUsers();
//        Long id = user1.getId();
////        Long id = testUser.getId();
//
//        UserEditModel userEditModel = new UserEditModel();
//        userEditModel
//                .setId(id)
//                .setFirstName(user1.getFirstName())
//                .setLastName(user1.getLastName())
//                .setRoles(List.of(Role.ADMIN));
//
//
//        mockMvc.perform(patch("/users/edit/" + id)
//                        .flashAttr("userEditModel", userEditModel)
//                        .with(csrf()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/users/all"));
//
//
//        User user = mockUserRepository.findById(id).orElseThrow();
//        Assertions.assertTrue(user.getRoles().contains(Role.ADMIN));
//
//    }

    private User initUsers() {

//        UserRole userRole = new UserRole().setRole(Role.USER);
//        userRoleRepository.save(userRole);

        User user1 = new User();
        user1.setFirstName(F_NAME)
                .setLastName(L_NAME)
                .setUsername(USER_NAME)
                .setEmail(EMAIL)
                .setPassword(PASS);
             //   .setRoles(List.of(userRole));


        mockUserRepository.save(user1);

        return user1;
      //  return user1.getId();
    }
}
