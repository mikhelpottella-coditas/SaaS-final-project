package com.project.saas.service.global;


import com.project.saas.dto.global.request_dto.UserRequestDto;
import com.project.saas.dto.global.responceDto.StateManagerResponseDto;
import com.project.saas.entity.master.State;
import com.project.saas.entity.master.User;
import com.project.saas.enums.Role;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.global.UserRepository;
import com.project.saas.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StateManagerService {

    private final UserService userService;
    private final UserRepository userRepository;


    public StateManagerResponseDto getManagerById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND, "the manager not for found with the given id : "+id));
        if(!user.getRole().equals(Role.STATE_MANAGEMENT_STAFF)) throw new CustomException(HttpStatus.NOT_FOUND, "the manager not for found with the given id : "+id);

        log.info("checking if the state manager is assigned to any particular state");
        List<Long> ids = user.getManagerState()==null?null:user.getManagerState().stream().map(State::getId).toList();

        log.info("returning the state manager");
        return new StateManagerResponseDto(user.getId(), user.getFirstName(),user.getLastName(),user.getEmail(), user.getPhone(), user.getCreatedAt(),user.getUpdatedAt(),ids,ids!=null);

    }
}
