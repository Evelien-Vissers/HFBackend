package com.novi.HealForce.unittests;

import com.novi.dtos.ContactFormDTO;
import com.novi.dtos.UserFirstNameOutputDTO;
import com.novi.dtos.UserInputDTO;
import com.novi.dtos.UserOutputDTO;
import com.novi.entities.Role;
import com.novi.entities.User;
import com.novi.entities.Profile;
import com.novi.exceptions.ResourceNotFoundException;
import com.novi.repositories.RoleRepository;
import com.novi.repositories.UserRepository;
import com.novi.repositories.ProfileRepository;
import com.novi.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registerUser_SuccessfulRegistration() {
        // Arrange
        UserInputDTO inputDTO = new UserInputDTO();
        inputDTO.setFirstName("Test");
        inputDTO.setLastName("User");
        inputDTO.setEmail("testuser@example.com");
        inputDTO.setPassword("password");

        Role role = new Role();
        role.setRoleName("ROLE_USER");

        when(roleRepository.findByRoleNameIn(any())).thenReturn(Collections.singletonList(role));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // Act
        userService.registerUser(inputDTO);

        // Assert
        verify(userRepository).save(any(User.class));
    }

    @Test
    void authenticate_SuccessfulAuthentication() {
        // Arrange
        User user = new User();
        user.setEmail("testuser@example.com");
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail("testuser@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // Act
        boolean result = userService.authenticate("testuser@example.com", "password");

        // Assert
        assertTrue(result);
    }

    @Test
    void getAllUsers_ReturnsUserList() {
        // Arrange
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("User");

        when(userRepository.findAll()).thenReturn(List.of(user));

        // Act
        List<UserOutputDTO> result = userService.getAllUsers();

        // Assert
        assertFalse(result.isEmpty());
    }

    @Test
    void getUserById_UserExists_ReturnsUser() {
        // Arrange
        User user = new User();
        user.setFirstName("Test");
        user.setLastName("User");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        UserOutputDTO result = userService.getUserById(1L);

        // Assert
        assertEquals("Test", result.getFirstName());
    }

    @Test
    void getUserById_UserNotFound_ThrowsException() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));
    }

    @Test
    void updateUser_UserExists_UpdatesUser() {
        // Arrange
        User user = new User();
        user.setFirstName("OldName");
        user.setLastName("User");

        UserInputDTO inputDTO = new UserInputDTO();
        inputDTO.setFirstName("NewName");
        inputDTO.setLastName("User");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        userService.updateUser(1L, inputDTO);

        // Assert
        assertEquals("NewName", user.getFirstName());
    }

    @Test
    void deleteUser_UserAndProfileExist_DeletesUserAndProfile() {
        // Arrange
        User user = new User();
        Profile profile = new Profile();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(profileRepository.findByUser(user)).thenReturn(Optional.of(profile));

        // Act
        boolean result = userService.deleteUser(1L);

        // Assert
        assertTrue(result);
        verify(userRepository).deleteById(1L);
        verify(profileRepository).delete(profile);
    }

    @Test
    void processContactForm_NewUser_CreatesUser() {
        // Arrange
        ContactFormDTO contactFormDTO = new ContactFormDTO();
        contactFormDTO.setEmail("newuser@example.com");
        contactFormDTO.setFirstName("New");
        contactFormDTO.setLastName("User");

        when(userRepository.findByEmail("newuser@example.com")).thenReturn(Optional.empty());

        // Act
        userService.processContactForm(contactFormDTO);

        // Assert
        verify(userRepository).save(any(User.class));
    }

    @Test
    void processContactForm_ExistingUser_UpdatesQuestion() {
        // Arrange
        User user = new User();
        user.setEmail("existinguser@example.com");

        ContactFormDTO contactFormDTO = new ContactFormDTO();
        contactFormDTO.setEmail("existinguser@example.com");
        contactFormDTO.setQuestion("What is your favorite color?");

        when(userRepository.findByEmail("existinguser@example.com")).thenReturn(Optional.of(user));

        // Act
        userService.processContactForm(contactFormDTO);

        // Assert
        assertEquals("What is your favorite color?", user.getQuestion());
    }

    @Test
    void getFirstNameOfCurrentUser_ReturnsFirstName() {
        // Arrange
        User user = new User();
        user.setFirstName("CurrentUser");

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("currentuser@example.com");
        SecurityContextHolder.getContext().setAuthentication(auth);

        when(userRepository.findByEmail("currentuser@example.com")).thenReturn(Optional.of(user));

        // Act
        UserFirstNameOutputDTO result = userService.getFirstNameOfCurrentUser();

        // Assert
        assertEquals("CurrentUser", result.getFirstName());
    }
}