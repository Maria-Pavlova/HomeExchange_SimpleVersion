package com.example.homeexchange_simpleversion.web.controllers;

import com.example.homeexchange_simpleversion.config.CloudinaryConfig;
import com.example.homeexchange_simpleversion.models.dtos.bindingModels.MessageModel;
import com.example.homeexchange_simpleversion.models.entities.User;
import com.example.homeexchange_simpleversion.repositories.UserRepository;
import com.example.homeexchange_simpleversion.services.CloudinaryService;
import com.example.homeexchange_simpleversion.services.MessageService;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageService messageService;
    @MockBean
    private CloudinaryConfig config;

    @MockBean
    private CloudinaryService cloudinaryService;


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



    @Test
    @WithMockUser(username = "fromUser1")
    public void testSendMessage() throws Exception {

        User fromUser = new User();
        fromUser.setUsername("fromUser1")
                .setPassword("test1")
                .setFirstName("Ivan1")
                .setLastName("Ivanov1")
                .setEmail("ivan1@com");
        userRepository.save(fromUser);

        User toUser = new User();
        toUser.setUsername("toUser1")
                .setPassword("test2")
                .setFirstName("Ivan2")
                .setLastName("Ivanov2")
                .setEmail("ivan2@com");
        userRepository.save(toUser);

        MessageModel messageModel = new MessageModel();
        messageModel.setUsername(fromUser.getUsername())
                .setEmail(fromUser.getEmail())
                .setSubject("Test Subject")
                .setText("It`s test message");


        mockMvc.perform(post("/messages/send/" + toUser.getUsername())
                        .flashAttr("messageModel", messageModel)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

    }

    @Test
    @WithMockUser(username = "fromUser2")
    public void testSendMessage_InvalidParam() throws Exception {

        User fromUser = new User();
        fromUser.setUsername("fromUser2")
                .setPassword("test1")
                .setFirstName("Ivan1")
                .setLastName("Ivanov1")
                .setEmail("ivan1@com");
        userRepository.save(fromUser);

        User toUser = new User();
        toUser.setUsername("toUser2")
                .setPassword("test2")
                .setFirstName("Ivan2")
                .setLastName("Ivanov2")
                .setEmail("ivan2@com");
        userRepository.save(toUser);
        String toUsername = toUser.getUsername();

        mockMvc.perform(post("/messages/send/" + toUsername)
                        .param("username", fromUser.getUsername())
                        .param("email", fromUser.getEmail())
                        .param("subject", "test Subject")
                        .param("text", "Hi")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/messages/send"));
        userRepository.delete(toUser);
        userRepository.delete(fromUser);
    }

    private List<User> initUsers() {
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
    //    @Test
//    @WithMockUser(username = "testUser1")
//    public void testGetMessages() throws Exception {
//
//        mockMvc.perform(get("/messages"))
//                .andExpect(status().isOk())
//                .andExpect(model().attributeExists("messages"))
//                .andExpect(view().name("messages"));
//    }
}
