package com.project.saas.service;

import com.project.saas.config.tenantConfig.TenantContext;
import com.project.saas.dto.request_dto.LoginRequestDto;
import com.project.saas.dto.request_dto.UserRequestDto;
import com.project.saas.entity.master.User;
import com.project.saas.entity.master.UserRoles;
import com.project.saas.enums.Role;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.UserRepository;
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
    private final TenantUserRepo tenantUserRepo;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;

    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userRepository.findByEmail(username));
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
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();


        UserRoles userRoles = new UserRoles();
        userRoles.setRole(Role.OPERATIONAL_HEAD);
        user.addUserRole(userRoles);

        userRepository.save(user);

        return "user successfully saved";
    }

    public List<User> getByRole(Role role, Pageable pageable) {
        return userRepository.findUsersByUserRoles(role,pageable).getContent();
    }


}
