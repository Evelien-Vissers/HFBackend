package com.novi.HealForce.unittests;
import com.novi.dtos.ContactFormDTO;
import com.novi.dtos.UserInputDTO;
import com.novi.dtos.UserOutputDTO;
import com.novi.entities.Profile;
import com.novi.entities.User;
import com.novi.exceptions.ResourceNotFoundException;
import com.novi.repositories.ProfileRepository;
import com.novi.repositories.UserRepository;
import com.novi.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 1. Test voor het registreren van een nieuwe gebruiker
    @Test
    void testRegisterUser_Success() {
        // Arrange
        UserInputDTO inputDTO = new UserInputDTO();
        inputDTO.setFirstName("John");
        inputDTO.setLastName("Doe");
        inputDTO.setEmail("johndoe@example.com");

        User user = new User();
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        userService.registerUser(inputDTO);

        // Assert
        verify(userRepository, times(1)).save(any(User.class));
    }

    // 2. Test voor authenticatie succesvol
    @Test
    void testAuthenticate_Success() {
        // Arrange
        String email = "johndoe@example.com";
        String password = "password";
        User user = new User();
        user.setPassword("encodedPassword");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");

        // Act
        boolean isAuthenticated = userService.authenticate(email, password);

        // Assert
        assertTrue(isAuthenticated);
    }

    // 3. Test voor authenticatie gefaald
    @Test
    void testAuthenticate_Failure() {
        // Arrange
        String email = "johndoe@example.com";
        String password = "wrongPassword";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        boolean isAuthenticated = userService.authenticate(email, password);

        // Assert
        assertFalse(isAuthenticated);
    }

    // 4. Test voor het ophalen van alle gebruikers
    @Test
    void testGetAllUsers() {
        // Arrange
        User user = new User();
        user.setFirstName("John");

        List<User> users = new ArrayList<>();
        users.add(user);

        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<UserOutputDTO> userOutputDTOS = userService.getAllUsers();

        // Assert
        assertEquals(1, userOutputDTOS.size());
        assertEquals("John", userOutputDTOS.get(0).getFirstName());
    }

    // 5. Test voor het ophalen van een gebruiker op ID - succes
    @Test
    void testGetUserById_Success() {
        // Arrange
        User user = new User();
        user.setId(1L);
        user.setFirstName("John");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        UserOutputDTO userOutputDTO = userService.getUserById(1L);

        // Assert
        assertEquals("John", userOutputDTO.getFirstName());
    }

    // 6. Test voor het ophalen van een gebruiker op ID - niet gevonden
    @Test
    void testGetUserById_NotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));
    }

    // 7. Test voor het updaten van een gebruiker - succes
    @Test
    void testUpdateUser_Success() {
        // Arrange
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setFirstName("John");

        UserInputDTO inputDTO = new UserInputDTO();
        inputDTO.setFirstName("Jane");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

        // Act
        userService.updateUser(1L, inputDTO);

        // Assert
        verify(userRepository, times(1)).save(existingUser);
        assertEquals("Jane", existingUser.getFirstName());
    }

    // 8. Test voor het updaten van een gebruiker - niet gevonden
    @Test
    void testUpdateUser_NotFound() {
        // Arrange
        UserInputDTO inputDTO = new UserInputDTO();
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.updateUser(1L, inputDTO));
    }

    // 9. Test voor het verwijderen van een gebruiker en het profiel - succes
    @Test
    void testDeleteUser_Success() {
        // Arrange
        User user = new User();
        user.setId(1L);

        Profile profile = new Profile();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(profileRepository.findByUser(user)).thenReturn(Optional.of(profile));

        // Act
        boolean result = userService.deleteUser(1L);

        // Assert
        assertTrue(result);
        verify(userRepository, times(1)).deleteById(1L);
        verify(profileRepository, times(1)).delete(profile);
    }

    // 10. Test voor het verwijderen van een gebruiker en het profiel - gebruiker niet gevonden
    @Test
    void testDeleteUser_UserNotFound() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> userService.deleteUser(1L));
    }

    // 11. Test voor het verwerken van een contactformulier - nieuwe gebruiker
    @Test
    void testProcessContactForm_NewUser() {
        // Arrange
        ContactFormDTO contactFormDTO = new ContactFormDTO();
        contactFormDTO.setEmail("johndoe@example.com");
        contactFormDTO.setFirstName("John");
        contactFormDTO.setLastName("Doe");
        contactFormDTO.setQuestion("How can I reset my password?");

        when(userRepository.findByEmail("johndoe@example.com")).thenReturn(Optional.empty());

        // Act
        userService.processContactForm(contactFormDTO);

        // Assert
        verify(userRepository, times(1)).save(any(User.class));
    }

    // 12. Test voor het verwerken van een contactformulier - bestaande gebruiker
    @Test
    void testProcessContactForm_ExistingUser() {
        // Arrange
        User existingUser = new User();
        existingUser.setEmail("johndoe@example.com");

        ContactFormDTO contactFormDTO = new ContactFormDTO();
        contactFormDTO.setEmail("johndoe@example.com");
        contactFormDTO.setQuestion("How can I reset my password?");

        when(userRepository.findByEmail("johndoe@example.com")).thenReturn(Optional.of(existingUser));

        // Act
        userService.processContactForm(contactFormDTO);

        // Assert
        assertEquals("How can I reset my password?", existingUser.getQuestion());
        verify(userRepository, times(1)).save(existingUser);
    }
}
