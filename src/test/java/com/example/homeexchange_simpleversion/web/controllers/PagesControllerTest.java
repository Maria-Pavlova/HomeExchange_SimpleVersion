package com.example.homeexchange_simpleversion.web.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@SpringBootTest
@AutoConfigureMockMvc
public class PagesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    void getIndex() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
    @WithMockUser(username="user",roles={"USER"})
    @Test
    void getHomePage() throws Exception {
        mockMvc.perform(get("/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("home-page"));
    }

    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @Test
    void getAdminPage() throws Exception {
        mockMvc.perform(get("/pages/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin"));
    }
}
