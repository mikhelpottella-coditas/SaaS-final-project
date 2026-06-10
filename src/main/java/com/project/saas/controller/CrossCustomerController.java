package com.project.saas.controller;

import com.project.saas.dto.tenant.response.ComplaintResponseDto;
import com.project.saas.dto.tenant.response.CustomerBillResponseDto;
import com.project.saas.dto.tenant.response.MeterResponseDto;
import com.project.saas.enums.CustomerBillStatus;
import com.project.saas.enums.PaymentType;
import com.project.saas.service.tenant.CustomerBillService;
import com.project.saas.service.tenant.CustomerComplaintService;
import com.project.saas.service.tenant.TenantMeterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/tenant/customer")
public class CrossCustomerController {
    private final TenantMeterService tenantMeterService;
    private final CustomerComplaintService customerComplaintService;
    private final CustomerBillService customerBillService;


    // get all meters to a tenant

    @GetMapping("/{customerId}/meters")
    public ResponseEntity<List<MeterResponseDto>> getMeters(@PathVariable Long customerId){
        return ResponseEntity.ok(tenantMeterService.getByCustomerId(customerId));
    }


    // get all bills

    @GetMapping("/{doorNo}/complaints")
    public ResponseEntity<List<ComplaintResponseDto>> getComplaints(@PathVariable String doorNo){
        return ResponseEntity.ok(customerComplaintService.getByCustomer(doorNo));
    }


    //see all bills
    @GetMapping("/{doorNo}/bills")
    public ResponseEntity<List<CustomerBillResponseDto>> getBills(@PathVariable String doorNo,
                                                                  @RequestParam(defaultValue = "UNPAID", required = false)CustomerBillStatus customerBillStatus){
        return ResponseEntity.ok(customerBillService.getByCustomer(doorNo,customerBillStatus));
    }


    // raise complaint

    @PostMapping("/{doorNo}/raise-complaint/")
    public ResponseEntity<String> raiseComplaint(@PathVariable String doorNo, @RequestParam String complaint){
        return ResponseEntity.ok(customerComplaintService.raiseCustomerComplaint(doorNo,complaint));
    }


    // pay bills
    @PatchMapping("/paybill/{billId}/payment-type/{paymentType}")
    public ResponseEntity<String> payBill(@PathVariable Long billId,@PathVariable PaymentType paymentType){
        return ResponseEntity.ok(customerBillService.billPaid(billId,paymentType));
    }
}

