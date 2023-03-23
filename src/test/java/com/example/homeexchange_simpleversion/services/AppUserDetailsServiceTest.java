package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.models.entities.User;
import com.example.homeexchange_simpleversion.models.entities.UserRole;
import com.example.homeexchange_simpleversion.models.enums.Role;
import com.example.homeexchange_simpleversion.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentest4j.AssertionFailedError;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppUserDetailsServiceTest {
    private final String EXISTING_USERNAME = "admin";
    private final String NON_EXISTING_USERNAME = "non_exising";
    private AppUserDetailsService appUserDetailsServiceToTest;
    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void SetUp() {
        appUserDetailsServiceToTest = new AppUserDetailsService(mockUserRepository);
    }

    @Test
    void testUserFound() {
        UserRole testAdminRole = new UserRole().setRole(Role.ADMIN);
        UserRole testUserRole = new UserRole().setRole(Role.USER);
        User testUser = new User();
        testUser.setUsername(EXISTING_USERNAME).setPassword("admin")
                .setRoles(List.of(testAdminRole, testUserRole));

        when(mockUserRepository.findByUsername(EXISTING_USERNAME)).thenReturn(Optional.of(testUser));

        UserDetails details = appUserDetailsServiceToTest.loadUserByUsername(EXISTING_USERNAME);

        Assertions.assertNotNull(details);
        Assertions.assertEquals(EXISTING_USERNAME, details.getUsername());
        Assertions.assertEquals(testUser.getPassword(), details.getPassword());
        Assertions.assertEquals(2, details.getAuthorities().size());
        assertRole(details.getAuthorities(), "ROLE_ADMIN");
        assertRole(details.getAuthorities(), "ROLE_USER");
    }

    private void assertRole(Collection<? extends GrantedAuthority> authorities, String role) {
        authorities.stream()
                .filter(a -> role.equals(a.getAuthority()))
                .findAny()
                .orElseThrow(() -> new AssertionFailedError("Role " + role + " not found!"));
    }

    @Test
    void testUserNotFound() {
        assertThrows(UsernameNotFoundException.class,
                () -> appUserDetailsServiceToTest.loadUserByUsername(NON_EXISTING_USERNAME));
    }
}
