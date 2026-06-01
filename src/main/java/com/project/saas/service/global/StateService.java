package com.project.saas.service.global;

import com.project.saas.dto.global.request_dto.AssignStateRequestDto;
import com.project.saas.entity.master.State;
import com.project.saas.entity.master.User;
import com.project.saas.repo.StateRepo;
import com.project.saas.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
@Slf4j
public class StateService {

    private final StateRepo stateRepo;
    private final UserService userService;


    public State createState(AssignStateRequestDto  assignStateRequestDto){
        return State.builder()
                .name(assignStateRequestDto.name())
                .code(assignStateRequestDto.code())
                .build();
    }


    public String assignState(AssignStateRequestDto assignStateRequestDto) {
        State state = createState(assignStateRequestDto);
        User user = userService.findById(assignStateRequestDto.managerId());
        state.setManagerUser(user);
        stateRepo.save(state);
        return "assigned the manager : "+user.getFirstName()+" to the state : "+assignStateRequestDto.name();
    }


}
