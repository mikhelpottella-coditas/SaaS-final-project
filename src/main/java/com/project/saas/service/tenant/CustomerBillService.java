package com.project.saas.service.tenant;

import com.project.saas.dto.tenant.request.CustomerBillRequestDto;
import com.project.saas.dto.tenant.response.CustomerBillResponseDto;
import com.project.saas.entity.tenant.CustomerBill;
import com.project.saas.entity.tenant.TenantCustomerMeter;
import com.project.saas.entity.tenant.TenantMeter;
import com.project.saas.entity.tenant.TenantMeterPhoto;
import com.project.saas.enums.CustomerBillStatus;
import com.project.saas.repo.tenant.CustomerBillsRepo;
import com.project.saas.repo.tenant.TenantCustomerMeterRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomerBillService {

    private final CustomerBillsRepo customerBillsRepo;
    private final TenantCustomerMeterRepo tenantCustomerMeterRepo;

    public CustomerBillResponseDto generateBill(CustomerBillRequestDto customerBillRequestDto) {

        TenantCustomerMeter tenantCustomerMeter = tenantCustomerMeterRepo.findByDoorNo(customerBillRequestDto.doorNo());
        List<CustomerBill> customerBillList = customerBillsRepo.findAllByTenantCustomerMeter(tenantCustomerMeter);

        CustomerBill customerBill = customerBillList.getLast();
        TenantMeter tenantMeter = tenantCustomerMeter.getTenantMeter();

        LocalDateTime from;
        Double amount = 0.0;
        if(customerBill==null){
            amount = customerBillRequestDto.units()*tenantMeter.getRatePerUnit();
            from = LocalDateTime.now();
        }else {
            Long units =  customerBillRequestDto.units() - customerBill.getUnits();
            from = customerBill.getTo();
            amount = units * tenantMeter.getRatePerUnit();
        }



        CustomerBill newBill = CustomerBill.builder()
                .tenantCustomerMeter(tenantCustomerMeter)
                .units(customerBillRequestDto.units())
                .price(amount)
                .from(from)
                .to(LocalDateTime.now())
                .paymentType(null)
                .billStatus(CustomerBillStatus.PENDING).build();

        customerBillRequestDto.photoUrls().forEach(photoUrl -> {
            TenantMeterPhoto  meterPhoto= TenantMeterPhoto.builder().photoUrl(photoUrl).reference("meter photo").captureTime(Loca).bill().build().build();
        })

        customerBillsRepo.save(newBill);

        log.info("New bill has been saved successfully");
        return new CustomerBillResponseDto(newBill.getId(), customerBillRequestDto.customerId(), newBill.getFrom(),newBill.getTo() , customerBillRequestDto.meterId(), customerBillRequestDto.units(), amount, customerBillRequestDto.photoUrls(), null, null);

    }
}
