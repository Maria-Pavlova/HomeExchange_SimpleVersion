package com.example.homeexchange_simpleversion.web.rest;

import com.example.homeexchange_simpleversion.models.dtos.viewModels.MessageView;
import com.example.homeexchange_simpleversion.models.entities.User;
import com.example.homeexchange_simpleversion.repositories.UserRepository;
import com.example.homeexchange_simpleversion.services.MessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MessageRestControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private MessageService messageService;
   @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "testUser2")
    public void getCommentsTest_ReturnedAsJsonAndStatusOk() throws Exception {

        List<User> users = initUsers();
        User fromUser = users.get(0);
        User toUser = users.get(1);
        String username = toUser.getUsername();
        MessageView messageView = new MessageView("It`s test message",
                "Test Subject",
                fromUser.getUsername(),
                fromUser.getEmail(),
                LocalDateTime.now());
        when(messageService.getMessages(username)).thenReturn(List.of(messageView));

        mockMvc.perform(get("/api/messages"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].text", is("It`s test message")))
                .andExpect(jsonPath("$.[0].subject", is("Test Subject")))
                .andExpect(jsonPath("$.[0].fromUserUsername", is(fromUser.getUsername())));
        // TODO: 29.3.2023 г. return nothing if load Button !!
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
}
