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

    // Registreer een nieuwe gebruiker
    @Transactional
    public void registerUser(UserInputDTO userInputDTO) {
        User user = new User();
        user.setFirstName(userInputDTO.getFirstName());
        user.setLastName(userInputDTO.getLastName());
        user.setEmail(userInputDTO.getEmail());
        user.setPassword(userInputDTO.getPassword());

        //Standaardwaarden instellen
        user.setRole("User"); //Standaardwaarde voor role
        user.setAcceptedPrivacyStatementUserAgreement(false);
        user.setVerifiedEmail(false);
        user.setRegistrationDate(LocalDate.now());
        user.setHasCompletedQuestionnaire(false);

        //Sla de gebruiker op
        userRepository.save(user);
    }

    // Logica voor authenticatie
    public boolean authenticate(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && user.get().getPassword().equals(password);
    }

    // Get all users
    public List<UserOutputDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> mapToUserOutputDTO(user))
                .collect(Collectors.toList());
    }

    // Get a specific user by ID
    public UserOutputDTO getUserById(Long id) {
        return userRepository.findById(id)
                .map(this::mapToUserOutputDTO)
                .orElse(null);
    }

    //Methode om alleen de 'first name' van een gebruiker op te halen
    public String getFirstNameByUserId(Long ID) {
        return userRepository.findFirstNameById(ID)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }


    // Werk een specifieke gebruiker bij
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

        return mapToUserOutputDTO(user); //Geef de gemapte DTO terug
    }

    private UserOutputDTO mapToUserOutputDTO(User user) {
        return null;
    }

    public void deleteUser(Long id) {
        // Verwijder het profiel dat aan de gebruiker is gekoppeld
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Profile for user not found"));
        profileRepository.delete(profile);

        //Verwijder de gebruiker
        userRepository.deleteById(id);
    }
}

