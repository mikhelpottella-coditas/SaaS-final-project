package com.project.saas.service;

import com.project.saas.dto.request_dto.LoginRequestDto;
import com.project.saas.dto.request_dto.UserRequestDto;
import com.project.saas.entity.master.User;
import com.project.saas.entity.master.UserRoles;
import com.project.saas.enums.Role;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.UserRepository;
import com.project.saas.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    public String save(@Valid UserRequestDto userDto) {

        User user =  new User();
        user.setFirstName(userDto.firstName());
        user.setLastName(userDto.lastName());
        user.setEmail(userDto.email());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setPhone(userDto.phone());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        user.setTenant(null);

        UserRoles userRoles = new UserRoles();
        userRoles.setRole(Role.CUSTOMER);
        user.addUserRole(userRoles);



        userRepository.save(user);

        return "user successfully saved";

    }

    public String validateLogin(@Valid LoginRequestDto loginRequestDto) {
        log.info("Logging in user");
        User user = userRepository.findByEmail(loginRequestDto.email());
        if(user == null) {
            log.warn("Invalid credentials for user");
            throw  new CustomException(HttpStatus.NOT_FOUND, "Invalid credentials");
        }
        if(!passwordEncoder.matches(loginRequestDto.password(), user.getPassword())){
            log.warn("Invalid password for user");
            throw  new CustomException(HttpStatus.NOT_FOUND, "Invalid credentials");
        }

        log.info("User logged in successfully");
        return jwtUtil.generateToken(user);
    }
}
