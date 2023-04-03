package com.example.homeexchange_simpleversion;

import com.example.homeexchange_simpleversion.config.CloudinaryConfig;
import com.example.homeexchange_simpleversion.services.CloudinaryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class HomeExchangeSimpleVersionApplicationTests {
    @MockBean
    private CloudinaryConfig config;

    @MockBean
    private CloudinaryService cloudinaryService;
    @Test
    void contextLoads() {
    }

}
