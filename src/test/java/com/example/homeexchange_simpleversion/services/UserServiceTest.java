package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.models.dtos.bindingModels.UserRegisterDTO;
import com.example.homeexchange_simpleversion.models.entities.User;
import com.example.homeexchange_simpleversion.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.when;


    @ExtendWith(MockitoExtension.class)
    public class UserServiceTest {

        @Mock
        private PasswordEncoder mockPasswordEncoder;

        @Mock
        private UserRepository mockUserRepository;

        @Captor
        private ArgumentCaptor<User> userArgumentCaptor;

        private UserService testService;

        @BeforeEach
        void setUp() {
          //  testService = new UserService(mockUserRepository, mockPasswordEncoder);
            // TODO: 20.3.2023 г.
        }

        @Test
        void testUserRegistration_SaveInvoked() {

            // ARRANGE

            String testPassword = "topsecret";
            String encodedPassword = "encoded_password";

            UserRegisterDTO testRegistrationDTO = new UserRegisterDTO().
                    setEmail("test@example.com").
                    setFirstName("Test").
                    setLastName("Testov").
                    setPassword(testPassword);

            when(mockPasswordEncoder.encode(testRegistrationDTO.getPassword())).
                    thenReturn(encodedPassword);

            //ACT

          //  testService.registerAndLogin(testRegistrationDTO);

            //ASSERT
            Mockito.verify(mockUserRepository).save(userArgumentCaptor.capture());

            User actualSavedUser = userArgumentCaptor.getValue();
            Assertions.assertEquals(testRegistrationDTO.getEmail(), actualSavedUser.getEmail());
            Assertions.assertEquals(encodedPassword, actualSavedUser.getPassword());
        }
    }


