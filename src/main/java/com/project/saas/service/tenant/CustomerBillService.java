package com.project.saas.service.tenant;

import com.project.saas.dto.tenant.request.CustomerBillRequestDto;
import com.project.saas.dto.tenant.response.CustomerBillResponseDto;
import com.project.saas.entity.tenant.CustomerBill;
import com.project.saas.entity.tenant.TenantCustomerMeter;
import com.project.saas.entity.tenant.TenantMeter;
import com.project.saas.entity.tenant.TenantMeterPhoto;
import com.project.saas.enums.CustomerBillStatus;
import com.project.saas.enums.PaymentType;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.tenant.CustomerBillsRepo;
import com.project.saas.repo.tenant.TenantCustomerMeterRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

        CustomerBill customerBill = customerBillList.get(customerBillList.size() - 1);
        TenantMeter tenantMeter = tenantCustomerMeter.getTenantMeter();

        LocalDateTime from;
        Double amount = 0.0;
        if (customerBill == null) {
            amount = customerBillRequestDto.units() * tenantMeter.getRatePerUnit();
            from = LocalDateTime.now();
        } else {
            Long units = customerBillRequestDto.units() - customerBill.getUnits();
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

        customerBillRequestDto.meterPhotoRequestDtoList().forEach(photo -> {
            TenantMeterPhoto meterPhoto = TenantMeterPhoto.builder().photoUrl(photo.photoUrl()).reference(photo.reference()).captureTime(photo.captureTime()).build();
            customerBill.addPhoto(meterPhoto);
        });

        customerBillsRepo.save(newBill);

        log.info("New bill has been saved successfully");
        return new CustomerBillResponseDto(newBill.getId(), customerBillRequestDto.customerId(), newBill.getFrom(), newBill.getTo(),newBill.getPaidDate() ,customerBillRequestDto.meterId(), customerBillRequestDto.units(), amount, customerBillRequestDto.meterPhotoRequestDtoList(), null, null);
    }

    public List<CustomerBillResponseDto> getByCustomer(String doorNo, CustomerBillStatus customerBillStatus) {
        TenantCustomerMeter customerMeter = tenantCustomerMeterRepo.findByDoorNo(doorNo);

        List<CustomerBill> customerBillList = customerMeter.getCustomerBillList().stream().filter(c->c.getBillStatus() == customerBillStatus).toList();

        log.info("getting all the bills of the customer based on the filter");
        return customerBillList.stream().map(c-> new CustomerBillResponseDto(c.getId(), c.getTenantCustomerMeter().getCustomerId(), c.getFrom(), c.getTo(), c.getPaidDate(),c.getTenantCustomerMeter().getTenantMeter().getId(), c.getUnits(), c.getPrice(),null,c.getPaymentType(),c.getBillStatus())).toList();
    }

    public String billPaid(Long billId, PaymentType paymentType) {
        CustomerBill bill = customerBillsRepo.findById(billId).orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND, " bill not found with the given id"));

        bill.setBillStatus(CustomerBillStatus.PAID);
        bill.setPaidDate(LocalDateTime.now());
        bill.setPaymentType(paymentType);

        customerBillsRepo.save(bill);
        log.info("the bill is paid successfully");
        return "the bill is paid successfully";

    }
}
