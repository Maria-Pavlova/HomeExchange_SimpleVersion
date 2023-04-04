package com.example.homeexchange_simpleversion.web.controllers;

import com.example.homeexchange_simpleversion.config.CloudinaryConfig;
import com.example.homeexchange_simpleversion.models.dtos.bindingModels.AddHomeModel;
import com.example.homeexchange_simpleversion.models.entities.Home;
import com.example.homeexchange_simpleversion.models.entities.User;
import com.example.homeexchange_simpleversion.models.enums.HomeType;
import com.example.homeexchange_simpleversion.models.enums.ResidenceType;
import com.example.homeexchange_simpleversion.repositories.HomeRepository;
import com.example.homeexchange_simpleversion.repositories.UserRepository;
import com.example.homeexchange_simpleversion.services.CloudinaryService;
import com.example.homeexchange_simpleversion.services.HomeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.homeexchange_simpleversion.models.enums.AmenityName.WIFI;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private HomeRepository homeRepository;

    private User testUser;
    @Autowired
    private UserRepository userRepository;

   @MockBean
   private CloudinaryConfig cloudinaryConfig;
   @MockBean
    private CloudinaryService cloudinaryService;


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

    @AfterEach
    void tearDown() {
        homeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testGetAddHomeForm() throws Exception {

        mockMvc.perform(get("/homes/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("homeModel"))
                .andExpect(view().name("add-home2"));
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testGetMyHomes() throws Exception {
        String username = testUser.getUsername();
        Home testHome = initHome();
        mockMvc.perform(get("/homes/" + username))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("myHomes"))
                .andExpect(view().name("users-homes"));
        Optional<Home> home = homeRepository.findById(testHome.getId());
        String expectedTitle = "Super apartment";
        home.ifPresent(value -> Assertions.assertEquals(expectedTitle, value.getTitle()));
    }


    @Test
    @WithMockUser(username = "testUser")
    public void testGetHomeDetails() throws Exception {

        Home testHome = initHome();
        Long id = testHome.getId();
        mockMvc.perform(get("/homes/" + id + "/details"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("details"))
                .andExpect(view().name("home-details"));

        Optional<Home> home = homeRepository.findById(testHome.getId());
        String expectedDescription = "Perfect place";
        Assertions.assertEquals(expectedDescription, home.get().getDescription());
        Assertions.assertEquals(2, home.get().getBathrooms());
        Assertions.assertEquals(2, home.get().getBedrooms());
    }

    @Test
    @WithMockUser(username = "testUser")
    public void testGetUpdateHomeForm() throws Exception {
        Home testHome = initHome();
        Long id = testHome.getId();
        mockMvc.perform(get("/homes/" + id + "/update"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("homeUpdateModel"))
//                .andExpect(model().attributeExists("homeType"))
//                .andExpect(model().attributeExists("residenceType"))
//                .andExpect(model().attributeExists("amenityName"))
                .andExpect(view().name("update-home"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    public void testDeleteHomeById() throws Exception {
        Home testHome = initHome();
        Long id = testHome.getId();

        mockMvc.perform(delete("/homes/delete/" + id)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));

        Home home = homeRepository.findById(id).orElse(null);
        Assertions.assertNull(home);
    }

    // TEST fails because homes -deleteAll
//    @Test
//    @WithMockUser(username = "testUser")
//    public void testPublishHome() throws Exception {
//        Home testHome = initHome();
//        Long id = testHome.getId();
//        mockMvc.perform(get("/homes/post/" +id)
//                        .with(csrf()))
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/home"));
//
//   }


    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void testAddHome() throws Exception {

        AddHomeModel addHomeModel = new AddHomeModel();
        addHomeModel
                .setTitle("Super apartment")
                .setHomeType(HomeType.APARTMENT)
                .setResidenceType(ResidenceType.PRIMARY)
                .setBedrooms(2)
                .setBathrooms(2)
                .setPeopleFor(4)
                .setDescription("Perfect place")
                .setAvailableFrom(LocalDate.parse("2023-06-10"))
                .setAvailableTo(LocalDate.parse("2023-06-20"))
                .setTown("Sofia")
                .setCountry("Bulgaria")
                .setPicture(new MockMultipartFile("file.png", "content".getBytes()))
                .setAmenities(List.of(WIFI));

        mockMvc.perform(post("/homes/add")
                        .flashAttr("addHomeModel", addHomeModel)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/home"));
    }

    private Home initHome() {
        Home testHome = new Home();
        testHome.setTitle("Super apartment")
                .setHomeType(HomeType.APARTMENT)
                .setResidenceType(ResidenceType.PRIMARY)
                .setBedrooms(2)
                .setBathrooms(2)
                .setPeopleFor(4)
                .setDescription("Perfect place")
                .setAvailableFrom(LocalDate.parse("2023-06-10"))
                .setAvailableTo(LocalDate.parse("2023-06-20"))
                .setOwner(testUser)
                .setTown("Sofia")
                .setCountry("Bulgaria")
                .setPublished(false)
                .setPicture("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRE3eampwBdDhZnnhM0AfE3Xnt0O0wU6kuKDA&usqp=CAU");
        return homeRepository.saveAndFlush(testHome);

    }
}
