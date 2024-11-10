package com.novi.controllers;

import com.novi.dtos.*;
import com.novi.helpers.UrlHelper;
import com.novi.mappers.UserDTOMapper;
import com.novi.entities.User;
import com.novi.exceptions.ResourceNotFoundException;
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

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserInputDTO userInputDTO) {
        userService.registerUser(userInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully!");
    }


    @GetMapping("/{Id}")
    public ResponseEntity<UserOutputDTO> getUserById(@PathVariable Long Id) {
        UserOutputDTO userDTO = userService.getUserById(Id);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            throw new ResourceNotFoundException("User with id " + Id + " not found");
        }
    }


    @PutMapping("/{Id}")
    public ResponseEntity<UserOutputDTO> updateUser(@PathVariable Long Id,
                                                    @RequestBody UserInputDTO userDTO) {
        UserOutputDTO updatedUserDTO = userService.updateUser(Id, new UserInputDTO());
        if (userDTO != null) {
            return ResponseEntity.ok(updatedUserDTO);
        } else {
            throw new ResourceNotFoundException("User with id " + Id + " not found");
        }
    }

    @GetMapping("/getall")
    public ResponseEntity<List<UserOutputDTO>> getAllUsers() {
        List<UserOutputDTO> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long Id) {
        boolean isDeleted = userService.deleteUser(Id);
        if (isDeleted) {
            System.out.println("User succesfully deleted");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new ResourceNotFoundException("User with id " + Id + " not found");
        }
    }

    @PostMapping("/contact")
    public ResponseEntity<String> submitContactForm(@RequestBody ContactFormDTO contactFormDTO) {
        userService.processContactForm(contactFormDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Contact form submitted");
    }

    @PostMapping("/create")
    public ResponseEntity<?> CreateUser(@RequestBody @Valid UserRequestDTO userDTO) {
        User user = userDTOMapper.mapToModel(userDTO);
        user.setEnabled(true);

        if(!apiUserDetailService.createUserWithRoles(user, defaultRoles)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.created(UrlHelper.getCurrentUrlWithId(request, user.getId())).build();
    }

    @GetMapping("/firstname")
    public ResponseEntity<UserFirstNameOutputDTO> getCurrentUserFirstName() {
        UserFirstNameOutputDTO firstNameDTO = userService.getFirstNameOfCurrentUser();
        return ResponseEntity.ok(firstNameDTO);
    }
}


