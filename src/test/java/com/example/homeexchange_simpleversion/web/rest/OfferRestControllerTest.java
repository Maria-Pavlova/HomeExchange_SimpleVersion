package com.example.homeexchange_simpleversion.web.rest;

import com.example.homeexchange_simpleversion.config.CloudinaryConfig;
import com.example.homeexchange_simpleversion.models.entities.Home;
import com.example.homeexchange_simpleversion.models.entities.Offer;
import com.example.homeexchange_simpleversion.models.entities.User;
import com.example.homeexchange_simpleversion.models.enums.HomeType;
import com.example.homeexchange_simpleversion.models.enums.ResidenceType;
import com.example.homeexchange_simpleversion.repositories.HomeRepository;
import com.example.homeexchange_simpleversion.repositories.OfferRepository;
import com.example.homeexchange_simpleversion.repositories.UserRepository;
import com.example.homeexchange_simpleversion.services.CloudinaryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class OfferRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private HomeRepository homeRepository;
    @Autowired
    private UserRepository userRepository;
    @MockBean
    private CloudinaryConfig config;

    @MockBean
    private CloudinaryService cloudinaryService;

    private User testUser;


    @BeforeEach
    void SetUp() {
        testUser = new User();
        testUser.setUsername("Ivan")
                .setPassword("test")
                .setFirstName("Ivan")
                .setLastName("Ivanov")
                .setEmail("ivan@com");
        testUser = userRepository.save(testUser);
    }

    @AfterEach
    void tearDown() {
        offerRepository.deleteAll();
        homeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testGetAllOffers() throws Exception {
        initOffers();
        mockMvc.perform(get("/api/offers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testGetOfferById() throws Exception {
        long id = initOffers();
        mockMvc.perform(get("/api/offers/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.home.title", is("TestHome")));
    }

    @Test
    void testGetOfferByIdThrowsIfOfferIdDoesNotExist() throws Exception {
        long id = initOffers();
        mockMvc.perform(get("/api/offers/" + id + 5))
                .andExpect(status().isNotFound());
    }


    private long initOffers() {
        Home testHome1 = new Home();
        testHome1.setTitle("TestHome")
                .setTown("Sofia")
                .setCountry("Bulgaria")
                .setPeopleFor(4)
                .setResidenceType(ResidenceType.PRIMARY)
                .setHomeType(HomeType.APARTMENT)
                .setBedrooms(1)
                .setBathrooms(1)
                .setAvailableFrom(LocalDate.parse("2023-06-10"))
                .setAvailableTo(LocalDate.parse("2023-06-30"))
                .setDescription("test home")
                .setOwner(testUser)
                .setPublished(true);

        homeRepository.save(testHome1);

        Offer testOffer1 = new Offer();
        testOffer1.setHome(testHome1);
        testOffer1.setOfferCreated(LocalDateTime.now());
        offerRepository.save(testOffer1);
        return testOffer1.getId();
    }
}
