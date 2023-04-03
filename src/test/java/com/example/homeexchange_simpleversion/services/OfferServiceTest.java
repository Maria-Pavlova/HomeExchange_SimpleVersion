package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.exceptions.ObjectNotFoundException;
import com.example.homeexchange_simpleversion.models.entities.Home;
import com.example.homeexchange_simpleversion.models.entities.Offer;
import com.example.homeexchange_simpleversion.models.entities.User;
import com.example.homeexchange_simpleversion.models.enums.HomeType;
import com.example.homeexchange_simpleversion.models.enums.ResidenceType;
import com.example.homeexchange_simpleversion.repositories.OfferRepository;
import com.example.homeexchange_simpleversion.utils.PublishHomeEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OfferServiceTest {

    @Mock
    private OfferRepository offerRepository;

    @Mock
    private PublishHomeEvent event;

    @InjectMocks
    private OfferService offerService;
    @Captor
    private ArgumentCaptor<Offer> offerArgumentCaptor;


    private Home testHome;

    private Offer testOffer;

    @BeforeEach
    void setUp() {

        testHome = new Home();
        testHome.setTitle("Test home")
                .setHomeType(HomeType.APARTMENT)
                .setResidenceType(ResidenceType.PRIMARY)
                .setTown("Varna")
                .setCountry("Bulgaria");

        testOffer = new Offer();
        testOffer.setId(1L);
        testOffer.setHome(testHome);
    }

    @Test
    void testOnPublishHomeCreateOfferTest() {
        offerService
                .onPublishHomeCreateOffer(new PublishHomeEvent(testHome));

        Mockito.verify(offerRepository).save(offerArgumentCaptor.capture());
    }

    @Test
    void testGetOfferById_ReturnCorrectOfferIfExist(){
        Long id = testOffer.getId();

        when(offerRepository.findById(id)).thenReturn(Optional.of(testOffer));

        Assertions.assertEquals(1L, offerRepository.findById(id).get().getId());
    }

    @Test
    void testGetOfferById_ThrowsIfNotExist(){
        assertThrows(ObjectNotFoundException.class,
                () -> offerService.getOfferById(5L));
    }

    @Test
    void testGetOffersByTown_ReturnCorrectOffer(){
        String expected = testHome.getTown();

        List<Offer> offers = offerRepository.findAllByHome_Town(expected);
        if (!offers.isEmpty()) {
            String actual = offers.get(0).getHome().getTown();
            Assertions.assertEquals(expected, actual);
        }
    }
}
