package com.project.saas.controller.globalManagement;

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
@RequestMapping("/global/managemant")
public class ManagementController {

    private final InvitationService invitationService;


    @PostMapping("/invite/operational-head")
    public ResponseEntity<String> inviteOperationalHead(@Valid @RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteOperationHead(invitationRequestDto));
    }

    @PostMapping("/invite/sales-point")
    public ResponseEntity<String> inviteSalesPoint(@Valid @RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteSalesPoint(invitationRequestDto));
    }

    @PostMapping("/invite/state-manager")
    public ResponseEntity<String> inviteStateManger(@Valid @RequestBody InvitationRequestDto invitationRequestDto){
        return ResponseEntity.ok(invitationService.inviteStateManager(invitationRequestDto));
    }


}
