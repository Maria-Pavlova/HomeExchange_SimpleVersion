package com.example.homeexchange_simpleversion.web.controllers;

import com.example.homeexchange_simpleversion.config.CloudinaryConfig;
import com.example.homeexchange_simpleversion.models.dtos.bindingModels.UserEditModel;
import com.example.homeexchange_simpleversion.models.entities.User;
import com.example.homeexchange_simpleversion.models.entities.UserRole;
import com.example.homeexchange_simpleversion.models.enums.Role;
import com.example.homeexchange_simpleversion.repositories.UserRepository;
import com.example.homeexchange_simpleversion.repositories.UserRoleRepository;
import com.example.homeexchange_simpleversion.services.CloudinaryService;
import com.example.homeexchange_simpleversion.services.MessageService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
    @Autowired
    private MessageService messageService;
    @MockBean
    private CloudinaryConfig cloudinaryConfig;
    @MockBean
    private CloudinaryService cloudinaryService;

    @Order(1)
    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void testGetAllUsers() throws Exception {
        User user = initUsers();

        mockMvc.perform(get("/users/all"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("users"))
                .andExpect(view().name("all-users"));
        Optional<User> byUsername = mockUserRepository.findByUsername(USER_NAME);
        System.out.println(byUsername);
        Assertions.assertTrue(byUsername.isPresent());

    }


@Order(2)
    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
   public void testGetUserById() throws Exception {
    UserRole userRole = new UserRole();
    userRole.setRole(Role.USER);
    userRoleRepository.saveAndFlush(userRole);
    User user1 = new User();
    user1.setFirstName(F_NAME)
            .setLastName(L_NAME)
            .setUsername("test2")
            .setEmail(EMAIL)
            .setPassword(PASS)
            .setRoles(List.of(userRole));
    mockUserRepository.saveAndFlush(user1);

        long id = user1.getId();
        mockMvc.perform(get("/users/edit/" + id))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userEditModel"))
                .andExpect(model().attributeExists("listRoles"))
                .andExpect(view().name("user-edit"));
        mockUserRepository.delete(user1);
    }

    @Order(3)
    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void testUpdateUserById() throws Exception {



        UserRole userRole = new UserRole();
        userRole.setRole(Role.USER);
        userRoleRepository.saveAndFlush(userRole);
        User user3 = new User();
        user3.setFirstName(F_NAME)
                .setLastName(L_NAME)
                .setUsername("test3")
                .setEmail(EMAIL)
                .setPassword(PASS)
                .setRoles(List.of(userRole));
        mockUserRepository.saveAndFlush(user3);
        Long id = user3.getId();
        UserEditModel userEditModel = new UserEditModel();
        userEditModel
                .setId(id)
                .setFirstName(user3.getFirstName())
                .setLastName(user3.getLastName())
                .setRoles(List.of(Role.ADMIN));


        mockMvc.perform(patch("/users/edit/" + id)
                        .flashAttr("userEditModel", userEditModel)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/all"));


        User user = mockUserRepository.findById(id).orElseThrow();
        Optional<UserRole> userRoleOpt = user.getRoles()
                .stream()
                .findFirst();
        if (userRoleOpt.isPresent()) {
            String roleName = userRoleOpt.get().getRole().name();

            Assertions.assertEquals("ADMIN", roleName);
        }
        mockUserRepository.delete(user3);
    }

    private User initUsers() {

        UserRole userRole = new UserRole();
        userRole.setRole(Role.USER);
        userRoleRepository.saveAndFlush(userRole);
        UserRole adminRole = new UserRole();
        userRole.setRole(Role.ADMIN);
        userRoleRepository.saveAndFlush(adminRole);

        User user1 = new User();
        user1.setFirstName(F_NAME)
                .setLastName(L_NAME)
                .setUsername(USER_NAME)
                .setEmail(EMAIL)
                .setPassword(PASS)
                .setRoles(List.of(userRole));
        return mockUserRepository.saveAndFlush(user1);

    }
}
