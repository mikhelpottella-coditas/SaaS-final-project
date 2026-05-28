package com.project.saas.service;

import com.project.saas.dto.request_dto.LoginRequestDto;
import com.project.saas.dto.request_dto.UserRequestDto;
import com.project.saas.entity.master.User;
import com.project.saas.entity.master.UserRoles;
import com.project.saas.enums.Role;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.UserRepository;
import com.project.saas.security.JwtUtil;
import jakarta.transaction.Transactional;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByEmail(username));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

    public String save(@Valid UserRequestDto userDto) {



        User user =  User.builder()
                .email(userDto.email())
                .password(passwordEncoder.encode(userDto.password()))
                .firstName(userDto.firstName())
                .lastName(userDto.lastName())
                .phone(userDto.phone())
                .tenant(null)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

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

        String result = "access token : "+ jwtUtil.generateToken(user) + "\n refresh token : " + refreshTokenService.createRefreshToken(user) ;

        log.info("User logged in successfully");
        return result;
    }

    public String saveOperationalHead(@Valid UserRequestDto userDto) {

        User user =  User.builder()
                .email(userDto.email())
                .password(passwordEncoder.encode(userDto.password()))
                .firstName(userDto.firstName())
                .lastName(userDto.lastName())
                .phone(userDto.phone())
                .tenant(null)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        user.setTenant(null);

        UserRoles userRoles = new UserRoles();
        userRoles.setRole(Role.OPERATIONAL_HEAD);
        user.addUserRole(userRoles);

        userRepository.save(user);

        return "operational head successfully saved";
    }


}
