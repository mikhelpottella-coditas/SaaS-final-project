package com.project.saas.controller.global;

import com.project.saas.dto.global.responceDto.ElectricianResponseDto;
import com.project.saas.service.global.ElectricianService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/global/cross")
public class CrossControllers {

    private final ElectricianService electricianService;


    // used by personnel
    @Operation(
            summary = "get all the electricians working in a particular area"
    )
    @GetMapping("/area/{areaId}/electricians")
    public ResponseEntity<List<ElectricianResponseDto>> getElectricianByArea(
            @PathVariable Long areaId,
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "5") int size,
            @RequestParam(required = false,defaultValue = "id") String sortBy,
            @RequestParam(required = false,defaultValue = "true") boolean ascending,
            @RequestParam(required = false,defaultValue = "") String search
    ){
        return ResponseEntity.ok(electricianService.getAllByArea(areaId,page,size,sortBy,ascending,search));
    }



}
