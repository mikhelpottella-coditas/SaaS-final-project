package com.project.saas.controller.global.globalManagement;

import com.project.saas.dto.request_dto.InvitationRequestDto;
import com.project.saas.service.InvitationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/global/state-management/")
public class StateManagementController{

    private final InvitationService invitationService;


    @PostMapping("/invite/district-manager")
    public ResponseEntity<String> inviteDistrictManager(@Valid @RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteDistrictManager(invitationRequestDto));
    }



}
