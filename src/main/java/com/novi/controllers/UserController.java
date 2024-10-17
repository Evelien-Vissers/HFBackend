package com.novi.controllers;

import com.novi.helpers.UrlHelper;
import com.novi.mappers.UserDTOMapper;
import com.novi.dtos.UserInputDTO;
import com.novi.dtos.UserOutputDTO;
import com.novi.dtos.UserRequestDTO;
import com.novi.entities.User;
import com.novi.exceptions.ResourceNotFoundException;
import com.novi.exceptions.UnauthorizedException;
import com.novi.security.ApiUserDetailService;
import com.novi.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")

//UserController richt zich alleen op het beheren van gebruikersinformatie
public class UserController {

    private final UserService userService;
    private final UserDTOMapper userDTOMapper;
    private final ApiUserDetailService apiUserDetailService;
    private final HttpServletRequest request;

    public UserController(UserService userService, UserDTOMapper userDTOMapper, ApiUserDetailService apiUserDetailService, HttpServletRequest request) {
        this.userService = userService;
        this.userDTOMapper = userDTOMapper;
        this.apiUserDetailService = apiUserDetailService;
        this.request = request;
    }

    // 1. POST - Registreer een nieuwe gebruiker
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserInputDTO userInputDTO) {
        userService.registerUser(userInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }

    // 2. POST - Gebruiker inloggen
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserInputDTO userInputDTO) {
        boolean isAuthenticated = userService.authenticate(userInputDTO.getEmail(), userInputDTO.getPassword());
        if (isAuthenticated) {
            return ResponseEntity.ok("Login successful");
        } else {
            throw new UnauthorizedException("Invalid credentials");
        }
    }

    // 3. GET /users/{Id} - Haal informatie op van een specifieke gebruiker
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

    // SECURITY USERCONTROLLERS

    //6.
    @PostMapping("/users")
    public ResponseEntity<?> CreateUser(@RequestBody @Valid UserRequestDTO userDTO) {
        User user = userDTOMapper.mapToModel(userDTO);
        user.setEnabled(true);
        if(!apiUserDetailService.createUser(user, defaultRoles)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(UrlHelper.getCurrentUrlWithId(request, user.getId())).build();
    }
}


