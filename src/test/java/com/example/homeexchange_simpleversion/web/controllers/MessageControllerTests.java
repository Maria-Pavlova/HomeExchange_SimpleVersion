package com.example.homeexchange_simpleversion.web.controllers;

import com.example.homeexchange_simpleversion.models.entities.User;
import com.example.homeexchange_simpleversion.repositories.HomeRepository;
import com.example.homeexchange_simpleversion.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
//    private User testUser1;
//    private User testUser2;



    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "testUser1")
    public void testGetMessages() throws Exception {

        mockMvc.perform(get("/messages"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("messages"))
                .andExpect(view().name("messages"));
    }

    @Test
    @WithMockUser(username = "testUser1")
    public void testGetSendMessageForm() throws Exception {

        List<User> users = initUsers();

        String username = users.get(1).getUsername();
        mockMvc.perform(get("/messages/send/" + username))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("toUser"))
                .andExpect(view().name("contact-form"));
    }

    private List<User> initUsers(){
        List<User> users = new ArrayList<>();
        User testUser1 = new User();
        testUser1.setUsername("testUser1")
                .setPassword("test1")
                .setFirstName("Ivan1")
                .setLastName("Ivanov1")
                .setEmail("ivan1@com");
        users.add(testUser1);


       User testUser2 = new User();
        testUser2.setUsername("testUser2")
                .setPassword("test2")
                .setFirstName("Ivan2")
                .setLastName("Ivanov2")
                .setEmail("ivan2@com");
        users.add(testUser2);
     return userRepository.saveAllAndFlush(users);
    }
}
