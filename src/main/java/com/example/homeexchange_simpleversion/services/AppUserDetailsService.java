package com.example.homeexchange_simpleversion.services;


import com.example.homeexchange_simpleversion.models.entities.UserRole;
import com.example.homeexchange_simpleversion.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      return  userRepository.findByUsername(username)
                .map(this::map)
                .orElseThrow(()-> new UsernameNotFoundException("User with name " + username + " not found."));
    }

    private UserDetails map(com.example.homeexchange_simpleversion.models.entities.User userEntity){
        return
                User.builder().
                        username(userEntity.getUsername()).
                        password(userEntity.getPassword()).
                        authorities(userEntity.
                                getRoles().
                                stream().
                                map(this::map).
                                toList()).
                        build();
    }

    private GrantedAuthority map(UserRole userRole) {
        return new SimpleGrantedAuthority("ROLE_" + userRole.getRole().name());
    }
}
