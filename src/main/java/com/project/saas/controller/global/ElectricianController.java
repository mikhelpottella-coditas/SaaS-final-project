package com.project.saas.controller.global;

import com.project.saas.dto.global.responceDto.AssignWorkResponseDto;
import com.project.saas.enums.ComplaintStatus;
import com.project.saas.service.global.AssignWorkService;
import com.project.saas.service.global.ElectricianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/global/electrician/")
public class ElectricianController {

    private final AssignWorkService assignWorkService;


    @GetMapping("/{empId}/tasks")
    public ResponseEntity<List<AssignWorkResponseDto>> getWorkById(@PathVariable Long empId,
                                                                   @RequestParam(required = false,defaultValue = "0") int page,
                                                                   @RequestParam(required = false,defaultValue = "5") int size,
                                                                   @RequestParam(required = false,defaultValue = "id") String sortBy,
                                                                   @RequestParam(required = false,defaultValue = "true") boolean ascending,
                                                                   @RequestParam(required = false,defaultValue = "") String search,
                                                                   @RequestParam(required = false,defaultValue = "IN_PROGRESS")ComplaintStatus filter) {
        return ResponseEntity.ok(assignWorkService.getByEmpId(empId,page,size,sortBy,ascending,search,filter));
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<AssignWorkResponseDto> getWorkById(@PathVariable Long taskId) {
        return ResponseEntity.ok(assignWorkService.getWorkById(taskId));
    }

    @PatchMapping("/{taskId}/markAsDone")
    public ResponseEntity<String> markAsDone(@PathVariable Long taskId) {
        return ResponseEntity.ok(assignWorkService.markAsDone(taskId));
    }

    @PatchMapping("/{taskId}/raise")
    public ResponseEntity<String> raiseTask(@PathVariable Long taskId) {
        return ResponseEntity.ok(assignWorkService.raiseTask(taskId));
    }

}

