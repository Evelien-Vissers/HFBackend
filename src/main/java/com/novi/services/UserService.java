package com.novi.services;

import com.novi.dtos.ContactFormDTO;
import com.novi.dtos.UserInputDTO;
import com.novi.dtos.UserOutputDTO;
import com.novi.entities.User;
import com.novi.entities.Profile;
import com.novi.exceptions.ResourceNotFoundException;
import com.novi.mappers.UserMapper;
import com.novi.repositories.UserRepository;
import com.novi.repositories.ProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, ProfileRepository profileRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 1a. Registreer een nieuwe gebruiker
    @Transactional
    public void registerUser(UserInputDTO userInputDTO) {
        User user = UserMapper.toUser(userInputDTO);

        //Standaardwaarden instellen
        user.setAcceptedPrivacyStatementUserAgreement(false);
        user.setRegistrationDate(LocalDate.now());

        //Sla de gebruiker op
        userRepository.save(user);
    }

    // 1b. Logica voor authenticatie
    public boolean authenticate(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && user.get().getPassword().equals(passwordEncoder.encode(password));
    }

    //2. Haal alle gebruikers op uit de database
    public List<UserOutputDTO> getAllUsers() {
        //Haal alle gebruiker op uit de repository
        List<User> users = userRepository.findAll();

        //Converteer de lijst van gebruikers naar een lijst van UserOutputDTO's
        return UserMapper.toUserOutputDTOList(users);
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
        // Zoek de gebruiker o.b.v het id (Long)
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Haal het profiel op dat aan gebruiker is gekoppeld
        Profile profile = profileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Profile for user not found"));

        //Verwijder het profiel dat aan de gebruiker is gekoppeld
        profileRepository.delete(profile);

        // Verwijder de gebruiker
        userRepository.deleteById(id);
        return true;
    }

    // 6. Verstuur contactformulier
    public void processContactForm(ContactFormDTO contactFormDTO) {
        //Check of gebruiker al bestaat obv het emailadres
        Optional<User> optionalUser = userRepository.findByEmail(contactFormDTO.getEmail());

            //Als gebruiker niet bestaat, maak dan een nieuwe User aan
        User user = optionalUser.orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(contactFormDTO.getEmail());
            newUser.setFirstName(contactFormDTO.getFirstName());
            newUser.setLastName(contactFormDTO.getLastName());
            return newUser;
        });

        //Zet vraag van het contactformulier in de User entity
        user.setQuestion(contactFormDTO.getQuestion());

        //Sla gebruiker op in de database
        userRepository.save(user);
    }

}

