package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.models.dtos.bindingModels.UserRegisterDTO;
import com.example.homeexchange_simpleversion.models.entities.User;
import com.example.homeexchange_simpleversion.models.entities.UserRole;
import com.example.homeexchange_simpleversion.models.enums.Role;
import com.example.homeexchange_simpleversion.repositories.UserRepository;
import com.example.homeexchange_simpleversion.repositories.UserRoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;

import java.util.function.Consumer;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private ModelMapper modelMapper;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    private UserService testService;
    @Mock
    private UserDetailsService userDetailsService;
    @Mock
    private SecurityContextRepository securityContextRepository;
    @Mock
    private UserRoleService userRoleService;


    @Mock
    private UserRoleRepository userRoleRepository;
    @Mock
    private Consumer<Authentication> successfulLoginProcessor;

    @BeforeEach
    void setUp() {
        testService = new UserService(mockUserRepository, mockPasswordEncoder,userDetailsService,
                modelMapper, securityContextRepository, userRoleService, userRoleRepository);

        UserRole userRole = new UserRole().setRole(Role.USER);
        userRoleRepository.save(userRole);
    }

//    @AfterEach
//    void tearDown(){
//        userRoleRepository.deleteAll();
//    }

    @Test
    void testUserRegistration_SaveInvoked() {

        String testPassword = "topsecret";
        String encodedPassword = "encoded_password";

        UserRegisterDTO testRegistrationDTO = new UserRegisterDTO().
                setEmail("test@example.com").
                setFirstName("Test").
                setLastName("Testov").
                setPassword(testPassword)
                .setConfirmPassword(testPassword)
                .setUsername("testUsername")
                .setPreferredDestinations("Sofia");

        when(mockPasswordEncoder.encode(testRegistrationDTO.getPassword())).
                thenReturn(encodedPassword);


        //ACT
       //   testService.registerAndLogin(testRegistrationDTO, successfulLoginProcessor);

        Mockito.verify(mockUserRepository).save(userArgumentCaptor.capture());

        User actualSavedUser = userArgumentCaptor.getValue();
        Assertions.assertEquals(testRegistrationDTO.getEmail(), actualSavedUser.getEmail());
        Assertions.assertEquals(encodedPassword, actualSavedUser.getPassword());
    }
}


