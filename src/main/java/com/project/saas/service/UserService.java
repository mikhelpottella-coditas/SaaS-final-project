package com.project.saas.service;

import com.project.saas.config.tenantConfig.TenantContext;
import com.project.saas.dto.global.request_dto.ChangePasswordRequestDto;
import com.project.saas.dto.global.request_dto.LoginRequestDto;
import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.entity.master.User;
import com.project.saas.enums.Role;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.global.UserRepository;
import com.project.saas.repo.tenant.TenantUserRepo;
import com.project.saas.security.JwtUtil;
import com.project.saas.service.global.RefreshTokenService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByEmail(username));
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND, "the user is not found"));
    }

    public List<User> getByRole(Role role, Pageable pageable) {
        return userRepository.findUsersByUserRoles(role,pageable).getContent();
    }



    public String validateLogin(@Valid LoginRequestDto loginRequestDto) {

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginRequestDto.email(), loginRequestDto.password());
        Authentication authentication = authenticationManager.authenticate(token);

        if(authentication.isAuthenticated()){
            User user = (User) authentication.getPrincipal();
            String tenant = TenantContext.getTenant()==null?"public":TenantContext.getTenant();

            String result = "access token : "+ jwtUtil.generateToken(loginRequestDto.email(),tenant) + "\n refresh token : " + refreshTokenService.createRefreshToken(user) ;
            log.info("User logged in successfully");
            return result;

        }
        else throw new CustomException(HttpStatus.UNAUTHORIZED, "Invalid username or password");


    }


    public String save(@Valid UserRequestDto userDto) {
        User user =  User.builder()
                .email(userDto.email())
                .password(passwordEncoder.encode(userDto.password()))
                .firstName(userDto.firstName())
                .lastName(userDto.lastName())
                .phone(userDto.phone())
                .role(Role.OPERATIONAL_HEAD)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();


        userRepository.save(user);
        return "user successfully saved";
    }



    public String changePassword(ChangePasswordRequestDto changePasswordRequestDto) {
        User user = findByUsername(changePasswordRequestDto.email()).orElseThrow(()-> new  CustomException(HttpStatus.NOT_FOUND, "the user is not found to change"));
        if(!passwordEncoder.matches(changePasswordRequestDto.oldPassword(), user.getPassword())) throw  new CustomException(HttpStatus.BAD_REQUEST, "wrong password !!!");
        user.setPassword(changePasswordRequestDto.newPassword());
        userRepository.save(user);
        log.info("password changed successfully for the email : {}",changePasswordRequestDto.email());
        return "password changed successfully";
    }


}
