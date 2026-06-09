package com.project.saas.controller.global;

import com.project.saas.dto.global.responceDto.CustomerResponseDto;
import com.project.saas.dto.tenant.request.CustomerBillRequestDto;
import com.project.saas.dto.tenant.response.CustomerBillResponseDto;
import com.project.saas.service.global.CustomerService;
import com.project.saas.service.tenant.CustomerBillService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/global/biller")
public class BillerController {

    private final CustomerBillService customerBillService;
    private final CustomerService customerService;


    @GetMapping("/area/{areaId}/customers")
    public ResponseEntity<List<CustomerResponseDto>> getCustomersByArea(@PathVariable Long areaId,
                                                                        @RequestParam(required = false,defaultValue = "0") int page,
                                                                        @RequestParam(required = false,defaultValue = "5") int size,
                                                                        @RequestParam(required = false,defaultValue = "id") String sortBy,
                                                                        @RequestParam(required = false,defaultValue = "true") boolean ascending,
                                                                        @RequestParam(required = false,defaultValue = "") String search) {
        return ResponseEntity.ok(customerService.getByArea(areaId,page,size,sortBy,ascending,search));
    }

    @GetMapping("/customer/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable Long id){
        return ResponseEntity.ok(customerService.getByCustomerId(id));
    }


    @PostMapping("/generate-bill")
    public ResponseEntity<CustomerBillResponseDto> generateBill(@RequestBody CustomerBillRequestDto customerBillRequestDto){
        return ResponseEntity.status(201).body(customerBillService.generateBill(customerBillRequestDto));
    }



}
