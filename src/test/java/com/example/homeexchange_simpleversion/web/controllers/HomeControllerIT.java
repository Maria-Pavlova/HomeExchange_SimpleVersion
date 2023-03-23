package com.example.homeexchange_simpleversion.web.controllers;

import com.example.homeexchange_simpleversion.models.entities.User;
import com.example.homeexchange_simpleversion.models.enums.AmenityName;
import com.example.homeexchange_simpleversion.models.enums.HomeType;
import com.example.homeexchange_simpleversion.models.enums.ResidenceType;
import com.example.homeexchange_simpleversion.repositories.HomeRepository;
import com.example.homeexchange_simpleversion.repositories.UserRepository;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

import static com.example.homeexchange_simpleversion.models.enums.AmenityName.WIFI;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WithMockUser("Ivan")
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private HomeRepository homeRepository;
    private User testUser;
    private UserRepository userRepository;


    @BeforeEach
    void SetUp() {

        testUser = new User();
        testUser.setUsername("Ivan")
                .setPassword("test")
                .setFirstName("Ivan")
                .setLastName("Ivanov")
                .setEmail("ivan@com");
      //  testUser = userRepository.save(testUser);
    }
    @Test
    void addHome() throws Exception {
//        final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.png");
//        final MockMultipartFile image = new MockMultipartFile("file", "test.png", "image/png", inputStream);


        mockMvc.perform(post("/homes/add")
                       // .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .param("title", "My home")
                        .param("homeType", "HOUSE")
                        .param("residenceType", "PRIMARY")
                        .param("town", "Sofia")
                        .param("country", "Bulgaria")
                        .param("bedrooms", "1")
                        .param("bathrooms", "1")
                        .param("peopleFor", "2")
                        .param("description", "the best home")
                        .param("availableFrom", String.valueOf(LocalDate.parse("2023-06-10")))
                        .param("availableTo", String.valueOf(LocalDate.parse("2023-06-30")))
                      //  .param("picture", image.getOriginalFilename())
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
//  Error message = Required part 'image' is not present.
        // TODO: 23.3.2023 г.
    }
}
