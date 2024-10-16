package com.novi.security;

import com.novi.entities.User;
import com.novi.mappers.UserMapper;
import com.novi.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ApiUserDetailService implements UserDetailsService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public ApiUserDetailService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;

    }

    @Transactional
    public boolean createUser(User user, List<String> roles) {
        var validRoles = roleRepository.findByRoleNameIn(roles);

        var user = userMapper.toEntity(userModel);
        for (Role role: validRoles ) {
            user.getRoles().add(role);
        }
        updateRolesWithUser(user);
        var savedUser = userRepository.save(user);
        userModel.setId(savedUser.getId());
        return savedUser != null;
    }

    private void updateRolesWithUser(User user) {
        for (Role role: user.getRoles()) {
            role.getUsers().add(user);
        }
    }
    @Transactional
    public boolean createUser(UserModel userModel, String[] roles) {
        return createUser(userModel, Arrays.asList(roles));
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = getUserByUserName(username);
        if (user.isEmpty()) { throw new UsernameNotFoundException(username);}
        return new ApiUserDetails(user.get());
    }


}
