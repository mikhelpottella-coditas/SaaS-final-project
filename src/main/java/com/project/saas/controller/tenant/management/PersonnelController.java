package com.project.saas.controller.tenant.management;

import com.project.saas.dto.global.responceDto.ElectricianResponseDto;
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
public class PersonnelController {


    private final PersonnelService personnelService;
    private final CustomerComplaintService complaintService;


    @GetMapping("/state/{id}/complaints")
    public ResponseEntity<List<ComplaintResponseDto>> getAllComplaintsByState(@PathVariable Long id,
                                                                              @RequestParam(required = false,defaultValue = "0") int page,
                                                                              @RequestParam(required = false,defaultValue = "5") int size,
                                                                              @RequestParam(required = false,defaultValue = "id") String sortBy,
                                                                              @RequestParam(required = false,defaultValue = "true") boolean ascending,
                                                                              @RequestParam(required = false,defaultValue = "") String search,
                                                                              @RequestParam ComplaintStatus filter){
        return ResponseEntity.ok(complaintService.getAllComplaintsByState(id,filter,page,size,sortBy,ascending,search));
    }

    @GetMapping("/complaint/{id}")
    public ResponseEntity<ComplaintResponseDto> getComplaintById(@PathVariable Long id){
        return ResponseEntity.ok(complaintService.getComplaintById(id));
    }


    @PatchMapping("/complaint/{complaintId}/assign-electrician/{electricianId}")
    public ResponseEntity<ComplaintResponseDto> assignElectrician(@PathVariable Long electricianId, @PathVariable Long complaintId){
        return ResponseEntity.ok(complaintService.assignElectrician(complaintId,electricianId));
    }


    @PatchMapping("/complaint/{id}/raise")
    public ResponseEntity<ComplaintResponseDto> raiseComplaint(@PathVariable Long id){
        return ResponseEntity.ok(complaintService.raiseComplaint(id));
    }

}
