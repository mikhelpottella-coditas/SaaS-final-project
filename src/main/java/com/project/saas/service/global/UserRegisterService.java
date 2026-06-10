package com.project.saas.service.global;

import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.entity.master.Invitation;
import com.project.saas.entity.master.User;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.global.UserRepository;
import com.project.saas.service.InvitationService;
import com.project.saas.service.TenantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserRegisterService {

    private final UserRepository userRepository;
    private final InvitationService invitationService;
    private final PasswordEncoder passwordEncoder;
    private final TenantService tenantService;

    public User userBuilder(UserRequestDto userDto) {
        return User.builder()
                .email(userDto.email())
                .password(passwordEncoder.encode(userDto.password()))
                .firstName(userDto.firstName())
                .lastName(userDto.lastName())
                .phone(userDto.phone())
                .tenant(null)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }


    public String saveUser(@Valid UserRequestDto userDto, String invitation) {
        if (Boolean.FALSE.equals(invitationService.validate(userDto.email(), invitation)))
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid invitation");
        Invitation invite = invitationService.getInvite(invitation);

        User user = userBuilder(userDto);

        user.setRole(invite.getRole());

        userRepository.save(user);
        log.info("registration successful with the name : {}", userDto.firstName());
        return "registration successful";
    }
}
