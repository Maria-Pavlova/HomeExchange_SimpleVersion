package com.example.homeexchange_simpleversion.web.rest;

import com.example.homeexchange_simpleversion.config.CloudinaryConfig;
import com.example.homeexchange_simpleversion.models.dtos.viewModels.HomeModelView;
import com.example.homeexchange_simpleversion.models.dtos.viewModels.OfferView;
import com.example.homeexchange_simpleversion.models.dtos.viewModels.UserProfile;
import com.example.homeexchange_simpleversion.models.enums.HomeType;
import com.example.homeexchange_simpleversion.models.enums.ResidenceType;
import com.example.homeexchange_simpleversion.services.CloudinaryService;
import com.example.homeexchange_simpleversion.services.OfferService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class OfferRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OfferService offerService;

    @MockBean
    private CloudinaryConfig config;

    @MockBean
    private CloudinaryService cloudinaryService;


    @Test
    void testGetAllOffers() throws Exception {
        HomeModelView home = initHome();
        OfferView offerView = new OfferView();
        offerView.setId(1L);
        offerView.setHome(home);
        List<OfferView> offers = List.of(offerView);

        given(offerService.getAllOffers()).willReturn(offers);
        mockMvc.perform(get("/api/offers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testGetOfferById() throws Exception {
        HomeModelView home = initHome();
        OfferView offerView = new OfferView();
                offerView.setId(1L);
                offerView.setHome(home);
        Long id = offerView.getId();

        given(offerService.findOfferById(id)).willReturn(Optional.of(offerView));

        mockMvc.perform(get("/api/offers/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.home.title", is("TestHome")));
    }

    @Test
    void testGetOfferByIdThrowsIfOfferIdDoesNotExist() throws Exception {

        given(offerService.findOfferById(1L)).willReturn(Optional.empty());

        mockMvc.perform(get("/api/offers/1"))
                .andExpect(status().isNotFound());
    }


    private HomeModelView initHome(){
        HomeModelView testHomeView = new HomeModelView();
        testHomeView.setTitle("TestHome")
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
                .setOwner(new UserProfile())
                .setPublished(true);
        return testHomeView;
    }

}
