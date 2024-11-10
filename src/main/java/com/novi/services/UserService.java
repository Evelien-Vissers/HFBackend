package com.novi.services;

import com.novi.dtos.ContactFormDTO;
import com.novi.dtos.UserFirstNameOutputDTO;
import com.novi.dtos.UserInputDTO;
import com.novi.dtos.UserOutputDTO;
import com.novi.entities.Role;
import com.novi.entities.User;
import com.novi.entities.Profile;
import com.novi.exceptions.ResourceNotFoundException;
import com.novi.mappers.UserMapper;
import com.novi.repositories.RoleRepository;
import com.novi.repositories.UserRepository;
import com.novi.repositories.ProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, ProfileRepository profileRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void registerUser(UserInputDTO userInputDTO) {
        User user = UserMapper.toUser(userInputDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAcceptedPrivacyStatementUserAgreement(false);
        user.setRegistrationDate(LocalDate.now());

        List<Role> roles = roleRepository.findByRoleNameIn(Collections.singletonList("ROLE_USER"));
        if (roles.isEmpty()) {
            throw new RuntimeException("Error: Role 'ROLE_USER' not found");
        }

        Role userRole = roles.get(0);
        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);
    }

    public boolean authenticate(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && user.get().getPassword().equals(passwordEncoder.encode(password));
    }

    public List<UserOutputDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return UserMapper.toUserOutputDTOList(users);
    }

    public UserOutputDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toUserOutputDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Transactional
    public UserOutputDTO updateUser(Long id, UserInputDTO userInputDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstName(userInputDTO.getFirstName());
        user.setLastName(userInputDTO.getLastName());
        user.setEmail(userInputDTO.getEmail());

        if (userInputDTO.getPassword() != null && !userInputDTO.getPassword().isEmpty()) {
            user.setPassword(userInputDTO.getPassword());
        }

        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        return UserMapper.toUserOutputDTO(user);
    }

    public boolean deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Profile for user not found"));

        profileRepository.delete(profile);
        userRepository.deleteById(id);
        return true;
    }

    public void processContactForm(ContactFormDTO contactFormDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(contactFormDTO.getEmail());

        User user = optionalUser.orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(contactFormDTO.getEmail());
            newUser.setFirstName(contactFormDTO.getFirstName());
            newUser.setLastName(contactFormDTO.getLastName());
            return newUser;
        });

        user.setQuestion(contactFormDTO.getQuestion());
        userRepository.save(user);
    }

    public UserFirstNameOutputDTO getFirstNameOfCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return new UserFirstNameOutputDTO(user.getFirstName());
    }
}
