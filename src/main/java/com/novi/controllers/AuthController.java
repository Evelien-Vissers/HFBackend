package com.novi.controllers;

import com.novi.dtos.LoginResponseDTO;
import com.novi.dtos.UserLoginRequestDTO;
import com.novi.security.ApiUserDetails;
import com.novi.entities.Role;
import com.novi.security.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.stream.Collectors;

@RestController
public class AuthController {
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager man, JwtService service, PasswordEncoder passwordEncoder) {
        this.authManager = man;
        this.jwtService = service;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginRequestDTO userLoginRequestDTO) {
        UsernamePasswordAuthenticationToken up =
                new UsernamePasswordAuthenticationToken(userLoginRequestDTO.getUserName(), userLoginRequestDTO.getPassword());

        //var test = passwordEncoder.encode(userLoginRequestDTO.getPassword());

        try {
            Authentication auth = authManager.authenticate(up);
            var ud = (ApiUserDetails) auth.getPrincipal();
            String token = jwtService.generateToken(ud);
            Long Id = ud.getUser().getId();

            String roles = ud.getUser().getRoles().stream()
                    .map(Role::getRoleName)
                    .collect(Collectors.joining(", "));
            LoginResponseDTO response = new LoginResponseDTO("Bearer " + token, Id, roles);

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body(response);
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
