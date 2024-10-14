package com.novi.services;

import com.novi.dtos.UserInputDTO;
import com.novi.dtos.UserOutputDTO;
import com.novi.entities.User;
import com.novi.entities.Profile;
import com.novi.exceptions.ResourceNotFoundException;
import com.novi.mappers.UserMapper;
import com.novi.repositories.UserRepository;
import com.novi.repositories.ProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    public UserService(UserRepository userRepository, ProfileRepository profileRepository) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
    }

    // 1a. Registreer een nieuwe gebruiker
    @Transactional
    public void registerUser(UserInputDTO userInputDTO) {
        User user = UserMapper.toUser(userInputDTO);

        //Standaardwaarden instellen
        user.setRole("User");
        user.setAcceptedPrivacyStatementUserAgreement(false);
        user.setVerifiedEmail(false);
        user.setRegistrationDate(LocalDate.now());
        user.setHasCompletedQuestionnaire(false);

        //Sla de gebruiker op
        userRepository.save(user);
    }

    // 1b. Logica voor authenticatie
    public boolean authenticate(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && user.get().getPassword().equals(password);
    }

    // 3. Get a specific user by ID
    public UserOutputDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toUserOutputDTO)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // 4. Werk een specifieke gebruiker bij
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

        //Update laatste inlogtijd
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        return UserMapper.toUserOutputDTO(user);
    }

    //5. Delete de gebruiker en het profiel dat daaraan is gekoppeld:
    public boolean deleteUser(Long id) {
        // Verwijder het profiel dat aan de gebruiker is gekoppeld
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile for user not found"));
        profileRepository.delete(profile);

        // Verwijder de gebruiker
        userRepository.deleteById(id);
        return false;
    }
}

