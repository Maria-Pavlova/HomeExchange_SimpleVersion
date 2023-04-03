package com.example.homeexchange_simpleversion.web.controllers;

import com.example.homeexchange_simpleversion.models.entities.User;
import com.example.homeexchange_simpleversion.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    private User testUser;


    @BeforeEach
    void SetUp() {
        testUser = new User();
        testUser.setUsername("testUser")
                .setPassword("test")
                .setFirstName("Ivan")
                .setLastName("Ivanov")
                .setEmail("ivan@com");
        testUser = userRepository.saveAndFlush(testUser);
    }

//    @AfterEach
//    void tearDown() {
//        userRepository.deleteAll();
//    }

    @Test
    @WithMockUser(username = "testUser")
    public void testGetUserProfile() throws Exception {

        mockMvc.perform(get("/users/profile"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("userProfile"))
                .andExpect(view().name("profile"));
    }
}