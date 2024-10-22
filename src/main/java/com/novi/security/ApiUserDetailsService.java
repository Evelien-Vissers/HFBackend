package com.novi.security;

import com.novi.entities.Role;
import com.novi.entities.User;
import com.novi.mappers.UserMapper;
import com.novi.repositories.RoleRepository;
import com.novi.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApiUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public ApiUserDetailsService(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

    }

    @Transactional
    public boolean createUserWithRoles(User user, List<String> roles) {
        List<Role> validRoles = roleRepository.findByRoleNameIn(roles);

        if (validRoles.isEmpty()) {
            throw new IllegalArgumentException("Geen geldige rollen gevonden voor de gebruiker");
        }

        for (Role role : validRoles) {
            user.getRoles().add(role);
        }

        // Koppel gebruiker aan de rollen
        linkUserToRoles(user);

        //Sla gebruiker op
        User savedUser = userRepository.save(user);
        return savedUser != null;
    }

    private void linkUserToRoles(User user) {
        for (Role role : user.getRoles()) {
            role.getUsers().add(user);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Gebruiker niet gevonden"));
        return new ApiUserDetails(user);
    }

}
