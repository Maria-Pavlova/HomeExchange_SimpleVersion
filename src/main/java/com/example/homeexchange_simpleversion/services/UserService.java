package com.example.homeexchange_simpleversion.services;

import com.example.homeexchange_simpleversion.models.dtos.bindingModels.UserEditModel;
import com.example.homeexchange_simpleversion.models.dtos.bindingModels.UserRegisterDTO;
import com.example.homeexchange_simpleversion.models.dtos.viewModels.UserProfile;
import com.example.homeexchange_simpleversion.models.entities.User;
import com.example.homeexchange_simpleversion.models.enums.Role;
import com.example.homeexchange_simpleversion.repositories.UserRepository;
import com.example.homeexchange_simpleversion.repositories.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService appUserDetailsService;
    private final ModelMapper modelMapper;
    private final SecurityContextRepository contextRepository;
    private final UserRoleService userRoleService;
    private final UserRoleRepository userRoleRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder, UserDetailsService appUserDetailsService,
                       ModelMapper modelMapper, SecurityContextRepository contextRepository,
                       UserRoleService userRoleService, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.appUserDetailsService = appUserDetailsService;
        this.modelMapper = modelMapper;
        this.contextRepository = contextRepository;
        this.userRoleService = userRoleService;
        this.userRoleRepository = userRoleRepository;
    }


    public void registerAndLogin(UserRegisterDTO userRegisterDTO,
                                 Consumer<Authentication> successfulLoginProcessor) {
        User newUser =
                new User()
                        .setFirstName(userRegisterDTO.getFirstName())
                        .setLastName(userRegisterDTO.getLastName())
                        .setUsername(userRegisterDTO.getUsername())
                        .setEmail(userRegisterDTO.getEmail())
                        .setRoles(userRoleRepository.findByRole(Role.USER).orElse(null))
                        .setPreferredDestinations(userRegisterDTO.getPreferredDestinations())
                        .setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));

        newUser = userRepository.save(newUser);

        UserDetails userDetails =
                appUserDetailsService.loadUserByUsername(newUser.getUsername());

        Authentication auth =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        userDetails.getPassword(),
                        userDetails.getAuthorities()
                );

        successfulLoginProcessor.accept(auth);

    }

    public UserProfile getUserProfile(String username){
        Optional<User> user = userRepository.findByUsername(username);
        return modelMapper.map(user.get(), UserProfile.class);
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<UserProfile> getAllUsers() {
      return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserProfile.class))
                .toList();
    }

    public UserProfile getUserProfileById(Long id) {
        return modelMapper.map(getUserById(id).get(), UserProfile.class);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);

    }

    public void updateUser(UserEditModel userEditModel) {
        User user = userRepository.findById(userEditModel.getId()).orElseThrow();
        user.setFirstName(userEditModel.getFirstName())
                .setLastName(userEditModel.getLastName())
                .setRoles(userEditModel.getRoles()
                        .stream()
                        .map(userRoleService::getByRole)
                        .toList());
        userRepository.save(user);
    }
}
