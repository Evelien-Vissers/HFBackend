package main.java.com.novi.services;

import main.java.com.novi.dto.UserDTO;
import main.java.com.novi.repositories.UserRepository;
import org.springframework.stereotype.Service;
import main.java.com.novi.entities.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user) {
        // Logica voor gebruikersregistratie
        userRepository.save(user);
    }

    public boolean authenticate(String email, String password) {
        // Logica voor authenticatie
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent() && user.get().getPassword().equals(password);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> updateUser(Long id, User updatedUser) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // Update de gegevens van de gebruiker
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword());
            user.setRole(updatedUser.getRole());
            user.setVerifiedEmail(updatedUser.getVerifiedEmail());
            user.setHasCompletedQuestionnaire(updatedUser.getHasCompletedQuestionnaire());
            userRepository.save(user);
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Methode om User om te zetten naar UserDTO
    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setRole(user.getRole());
        userDTO.setVerifiedEmail(user.getVerifiedEmail());
        userDTO.setRegistrationDate(user.getRegistrationDate());
        userDTO.setLastLogin(user.getLastLogin());
        userDTO.setHasCompletedQuestionnaire(user.getHasCompletedQuestionnaire());
        return userDTO;
    }
}
