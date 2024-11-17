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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Authentication authentication;
    @Mock
    private SecurityContext securityContext;
    @Mock
    private UserDetails userDetails;

    @InjectMocks
    private ProfileService profileService;

    private User user;
    private Profile profile;
    private ProfileInputDTO profileInputDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        profileService = new ProfileService(profileRepository, userRepository);

        user = new User();
        user.setEmail("test@example.com");

        profile = new Profile();
        profile.setId(1L);
        profile.setUser(user);

        profileInputDTO = new ProfileInputDTO();

        // Mock security context for getting the current user
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("test@example.com");
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
    }

    @Test
    void getCurrentUser_ShouldReturnCurrentUser() {
        // Act
        User currentUser = profileService.getCurrentUser();

        // Assert
        assertEquals("test@example.com", currentUser.getEmail());
    }

    @Test
    void saveProfile_ShouldSaveProfile() throws IOException {
        // Arrange
        MockMultipartFile profilePic = new MockMultipartFile("profilePic", "test.jpg", "image/jpeg", "Test Image Content".getBytes());
        when(profileRepository.save(any(Profile.class))).thenReturn(profile);

        // Act
        profileService.saveProfile(profileInputDTO, profilePic);

        // Assert
        verify(profileRepository, times(2)).save(any(Profile.class)); // Saved twice: once for profile, once for profilePic URL
    }

    @Test
    void saveProfile_ShouldNotSaveProfilePicWhenFileIsEmpty() throws IOException {
        // Arrange
        MockMultipartFile profilePic = new MockMultipartFile("profilePic", "", "image/jpeg", new byte[0]);
        when(profileRepository.save(any(Profile.class))).thenReturn(profile);

        // Act
        profileService.saveProfile(profileInputDTO, profilePic);

        // Assert
        verify(profileRepository, times(1)).save(any(Profile.class)); // Saved once without profilePic
    }

    @Test
    void getUserProfileByProfileID_ShouldReturnProfileOutputDTO() {
        // Arrange
        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));

        // Act
        ProfileOutputDTO result = profileService.getUserProfileByProfileID(1L);

        // Assert
        assertNotNull(result);
        verify(profileRepository, times(1)).findById(1L);
    }

    @Test
    void getUserProfileByProfileID_ShouldThrowExceptionWhenProfileNotFound() {
        // Arrange
        when(profileRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> profileService.getUserProfileByProfileID(1L));
        assertEquals("Profile not found", exception.getMessage());
    }

    @Test
    void findPotentialMatches_ShouldReturnPotentialMatches() {
        // Arrange
        profile.setConnectionPreference("Mix");
        when(profileRepository.findByUser(user)).thenReturn(Optional.of(profile));

        PotentialMatches match = new PotentialMatches(2L, "MatchName", "HealthChallenge", "Mix", "/images/test.jpg", "City", "Country");
        when(profileRepository.findPotentialMatches("Mix", 1L)).thenReturn(Arrays.asList(match));

        // Act
        List<PotentialMatchesOutputDTO> result = profileService.findPotentialMatches();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("MatchName", result.get(0).getHealforceName());
    }

    @Test
    void findPotentialMatches_ShouldThrowExceptionWhenUserHasNoProfile() {
        // Arrange
        when(profileRepository.findByUser(user)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(IllegalStateException.class, () -> profileService.findPotentialMatches());
        assertEquals("User has no profile. Please complete the questionnaire first.", exception.getMessage());
    }

    @Test
    void deleteProfile_ShouldDeleteProfile() {
        // Arrange
        when(profileRepository.findById(1L)).thenReturn(Optional.of(profile));

        // Act
        profileService.deleteProfile(1L);

        // Assert
        verify(profileRepository, times(1)).delete(profile);
    }

    @Test
    void deleteProfile_ShouldThrowExceptionWhenProfileNotFound() {
        // Arrange
        when(profileRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> profileService.deleteProfile(1L));
        assertEquals("Profile not found", exception.getMessage());
    }

    @Test
    void saveProfilePic_ShouldReturnProfilePicUrl() throws IOException {
        // Arrange
        Path tempFile = Files.createTempFile("test", ".jpg");
        Files.write(tempFile, "test image content".getBytes());

        MultipartFile mockFile = mock(MultipartFile.class);
        when(mockFile.getOriginalFilename()).thenReturn("example.jpg");
        when(mockFile.getInputStream()).thenReturn(Files.newInputStream(tempFile));

        Long profileId = 1L;
        String expectedUrl = "http://localhost:8080/images/example.jpg";

        // Act
        String result = profileService.saveProfilePic(mockFile, profileId);

        // Assert
        assertEquals(expectedUrl, result);

        // Cleanup
        Files.delete(tempFile);
    }
}
