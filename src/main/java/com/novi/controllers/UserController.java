package com.novi.controllers;

import com.novi.dtos.ContactFormDTO;
import com.novi.helpers.UrlHelper;
import com.novi.mappers.UserDTOMapper;
import com.novi.dtos.UserInputDTO;
import com.novi.dtos.UserOutputDTO;
import com.novi.dtos.UserRequestDTO;
import com.novi.entities.User;
import com.novi.exceptions.ResourceNotFoundException;
import com.novi.exceptions.UnauthorizedException;
import com.novi.security.ApiUserDetailsService;
import com.novi.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")

//UserController richt zich alleen op het beheren van gebruikersinformatie
public class UserController {

    private final UserService userService;
    private final UserDTOMapper userDTOMapper;
    private final ApiUserDetailsService apiUserDetailService;
    private final HttpServletRequest request;
    private final List<String> defaultRoles = List.of("ROLE_USER");

    public UserController(UserService userService, UserDTOMapper userDTOMapper, ApiUserDetailsService apiUserDetailService, HttpServletRequest request) {
        this.userService = userService;
        this.userDTOMapper = userDTOMapper;
        this.apiUserDetailService = apiUserDetailService;
        this.request = request;
    }

    // 1. POST - /users/register | Registreer een nieuwe gebruiker
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserInputDTO userInputDTO) {
        userService.registerUser(userInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }

    // 2. POST - /users/login | Gebruiker inloggen
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserInputDTO userInputDTO) {
        boolean isAuthenticated = userService.authenticate(userInputDTO.getEmail(), userInputDTO.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            throw new UnauthorizedException("Invalid credentials");
        }
    }

    // 3. GET /users/{Id} - Haal user-informatie op van een specifieke gebruiker
    @GetMapping("/{Id}")
    public ResponseEntity<UserOutputDTO> getUserById(@PathVariable Long Id) {
        UserOutputDTO userDTO = userService.getUserById(Id);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            throw new ResourceNotFoundException("User with id " + Id + " not found");
        }
    }

    // 4. PUT /users/{Id} - Werk informatie van een specifieke gebruiker bij
    @PutMapping("/{Id}")
    public ResponseEntity<UserOutputDTO> updateUser(@PathVariable Long Id, @RequestBody UserInputDTO userDTO) {
        UserOutputDTO updatedUserDTO = userService.updateUser(Id, new UserInputDTO());
        if (userDTO != null) {
            return ResponseEntity.ok(updatedUserDTO);
        } else {
            throw new ResourceNotFoundException("User with id " + Id + " not found");
        }
    }

    // 5. DELETE /users/{Id} - Verwijder een specifieke gebruiker
    @DeleteMapping("/{Id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long Id) {
        boolean isDeleted = userService.deleteUser(Id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new ResourceNotFoundException("User with id " + Id + " not found");
        }
    }

    // 6. CONTACT /users/contact | Gebruiker verstuurt bericht via contactformulier
    @PostMapping("/contact")
    public ResponseEntity<String> submitContactForm(@RequestBody ContactFormDTO contactFormDTO) {
        userService.processContactForm(contactFormDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Contact form submitted");
    }

    // SECURITY USERCONTROLLERS

    //7.
    @PostMapping("/create")
    public ResponseEntity<?> CreateUser(@RequestBody @Valid UserRequestDTO userDTO) {
        User user = userDTOMapper.mapToModel(userDTO);
        user.setEnabled(true);
        //Creeer gebruiker met standaardrollen
        if(!apiUserDetailService.createUserWithRoles(user, defaultRoles)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(UrlHelper.getCurrentUrlWithId(request, user.getId())).build();
    }
}


