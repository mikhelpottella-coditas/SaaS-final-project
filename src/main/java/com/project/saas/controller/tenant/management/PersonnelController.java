package com.project.saas.controller.tenant.management;

import com.project.saas.annotation.TenantValid;
import com.project.saas.dto.tenant.response.ComplaintResponseDto;
import com.project.saas.enums.ComplaintStatus;
import com.project.saas.service.tenant.CustomerComplaintService;
import com.project.saas.service.tenant.PersonnelService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/tenant/personnel")
@TenantValid
public class PersonnelController {


    private final PersonnelService personnelService;
    private final CustomerComplaintService complaintService;


    @Operation(summary = "get all the complaints that are received in a state BPO")
    @GetMapping("/state/{id}/complaints")
    public ResponseEntity<List<ComplaintResponseDto>> getAllComplaintsByState(@PathVariable Long id,
                                                                           @RequestParam ComplaintStatus filter){
        return ResponseEntity.ok(complaintService.getAllComplaintsByState(id,filter));
    }

    @Operation(summary = "get a complaint in detailed by id")
    @GetMapping("/complaint/{id}")
    public ResponseEntity<ComplaintResponseDto> getComplaintById(@PathVariable Long id){
        return ResponseEntity.ok(complaintService.getComplaintById(id));
    }


    @Operation(summary = "assign an electrician to the complaint to work on")
    @PatchMapping("/complaint/{complaintId}/assign-electrician/{electricianId}")
    public ResponseEntity<ComplaintResponseDto> assignElectrician(@PathVariable Long electricianId, @PathVariable Long complaintId){
        return ResponseEntity.ok(complaintService.assignElectrician(complaintId,electricianId));
    }


    @Operation(summary = "raise the complaint to m2 manager by any issue")
    @PatchMapping("/complaint/{id}/raise")
    public ResponseEntity<ComplaintResponseDto> raiseComplaint(@PathVariable Long id){
        return ResponseEntity.ok(complaintService.raiseComplaint(id));
    }

}
