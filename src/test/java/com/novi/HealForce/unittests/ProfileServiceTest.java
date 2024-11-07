package com.novi.HealForce.unittests;

import com.novi.dtos.PotentialMatchesOutputDTO;
import com.novi.dtos.ProfileInputDTO;
import com.novi.dtos.ProfileOutputDTO;
import com.novi.entities.PotentialMatches;
import com.novi.entities.Profile;
import com.novi.entities.User;
import com.novi.repositories.ProfileRepository;
import com.novi.repositories.UserRepository;
import com.novi.services.ProfileService;
import com.novi.mappers.ProfileMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfileServiceTest {

    @InjectMocks
    private ProfileService profileService;

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProfileMapper profileMapper;

    @Mock
    private Authentication authentication;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 1. Test voor het opslaan van een profiel
    @Test
    void testSaveProfile_Success() throws IOException {
        // Arrange
        ProfileInputDTO profileInputDTO = new ProfileInputDTO();
        profileInputDTO.setCity("Amsterdam");

        User currentUser = new User();
        Profile profile = new Profile();

        when(profileMapper.toEntity(profileInputDTO)).thenReturn(new Profile());
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(currentUser));

        // Simuleer de ingelogde gebruiker
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("johndoe@example.com");

        MockMultipartFile mockProfilePic = new MockMultipartFile(
                "profilePic",
                "profile-pic.jpg",
                "image/jpeg",
                "test image content".getBytes()
        );
        // Act
        profileService.saveProfile(profileInputDTO, mockProfilePic);

        // Assert
        verify(profileRepository, times(1)).save(any(Profile.class));
    }

    // 2. Test voor het ophalen van een profiel op ID - succes
    @Test
    void testGetUserProfileByProfileID_Success() {
        // Arrange
        Profile profile = new Profile();
        profile.setCity("Amsterdam");

        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));
        when(profileMapper.toProfileOutputDTO(profile)).thenReturn(new ProfileOutputDTO());

        // Act
        ProfileOutputDTO result = profileService.getUserProfileByProfileID(1L);

        // Assert
        assertNotNull(result);
        verify(profileRepository, times(1)).findById(1L);
    }

    // 3. Test voor het ophalen van een profiel op ID - niet gevonden
    @Test
    void testGetUserProfileByProfileID_NotFound() {
        // Arrange
        when(profileRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> profileService.getUserProfileByProfileID(1L));
    }

    // 4. Test voor het ophalen van de huidige ingelogde gebruiker - succes
    @Test
    void testGetCurrentUser_Success() {
        // Arrange
        User user = new User();
        user.setEmail("johndoe@example.com");

        when(userRepository.findByEmail("johndoe@example.com")).thenReturn(Optional.of(user));
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("johndoe@example.com");

        // Act
        User currentUser = profileService.getCurrentUser();

        // Assert
        assertNotNull(currentUser);
        assertEquals("johndoe@example.com", currentUser.getEmail());
    }

    // 5. Test voor het ophalen van de huidige ingelogde gebruiker - niet gevonden
    @Test
    void testGetCurrentUser_NotFound() {
        // Arrange
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("johndoe@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> profileService.getCurrentUser());
    }



    // 8. Test voor het vinden van potentiële matches
    @Test
    void testFindPotentialMatches_Success() {
        // Arrange
        Profile currentProfile = new Profile();
        currentProfile.setConnectionPreference("AllTypes");

        List<PotentialMatches> matches = new ArrayList<>();
        matches.add(new PotentialMatches("John", "Cancer", "Conventional", "Amsterdam", "Netherlands"));

        when(profileRepository.findPotentialMatches(anyString(), anyLong())).thenReturn(matches);
        when(profileRepository.findByUser(any(User.class))).thenReturn(Optional.of(currentProfile));

        // Simuleer de ingelogde gebruiker
        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("johndoe@example.com");
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        // Act
        List<PotentialMatchesOutputDTO> result = profileService.findPotentialMatches();

        // Assert
        assertEquals(1, result.size());
        assertEquals("John", result.get(0).getHealforceName());
    }

    // 9. Test voor het updaten van een profiel - succes
    @Test
    void testUpdateProfile_Success() {
        // Arrange
        Profile profile = new Profile();
        profile.setId(1L);

        ProfileInputDTO inputDTO = new ProfileInputDTO();
        inputDTO.setCity("Amsterdam");

        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));

        // Act
        profileService.updateProfile(1L, inputDTO);

        // Assert
        verify(profileRepository, times(1)).save(profile);
    }

    // 10. Test voor het updaten van een profiel - niet gevonden
    @Test
    void testUpdateProfile_NotFound() {
        // Arrange
        ProfileInputDTO inputDTO = new ProfileInputDTO();
        when(profileRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> profileService.updateProfile(1L, inputDTO));
    }

    // 11. Test voor het verwijderen van een profiel - succes
    @Test
    void testDeleteProfile_Success() {
        // Arrange
        Profile profile = new Profile();
        profile.setId(1L);

        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));

        // Act
        profileService.deleteProfile(1L);

        // Assert
        verify(profileRepository, times(1)).delete(profile);
    }

    // 12. Test voor het verwijderen van een profiel - niet gevonden
    @Test
    void testDeleteProfile_NotFound() {
        // Arrange
        when(profileRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> profileService.deleteProfile(1L));
    }

    // 13. Test voor het ophalen van profileID via email
    @Test
    void testGetProfileIdByEmail_Success() {
        // Arrange
        Profile profile = new Profile();
        profile.setId(1L);

        when(profileRepository.findByEmail("johndoe@example.com")).thenReturn(Optional.of(profile));

        // Act
        Long profileId = profileService.getProfileIdByEmail("johndoe@example.com");

        // Assert
        assertEquals(1L, profileId);
    }

    // 14. Test voor het ophalen van profileID via email - niet gevonden
    @Test
    void testGetProfileIdByEmail_NotFound() {
        // Arrange
        when(profileRepository.findByEmail("johndoe@example.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> profileService.getProfileIdByEmail("johndoe@example.com"));
    }
}

