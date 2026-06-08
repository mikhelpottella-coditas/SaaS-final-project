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
                                                                              @RequestParam ComplaintStatus filter){
        return ResponseEntity.ok(complaintService.getAllComplaintsByState(id,filter));
    }

}
