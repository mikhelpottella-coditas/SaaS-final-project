package com.project.saas.service.global;

import com.project.saas.dto.global.responceDto.SubscriptionBillsResponseDto;
import com.project.saas.entity.master.Tenant;
import com.project.saas.entity.master.TenantSubscriptionBill;
import com.project.saas.enums.BillStatus;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.global.TenantSubscriptionBillRepo;
import com.project.saas.service.TenantService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class TenantSubscriptionBillService {

    private final TenantSubscriptionBillRepo billRepo;
    private final TenantService tenantService;

    public String generateBill(Long tenantId) {
        log.info("start generating bill");
        Tenant tenant = tenantService.getById(tenantId);


        TenantSubscriptionBill bill = TenantSubscriptionBill.builder()
                .tenant(tenant)
                .billStatus(BillStatus.UNPAID)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(30))
                .amount(tenant.getSubscriptionAmount())
                .build();

        billRepo.save(bill);

        log.info("the new bill is generated to the tenant");
        return "the new bill is generated to the tenant";

    }

    public List<SubscriptionBillsResponseDto> getBills(Long id) {
        Tenant tenant = tenantService.getById(id);
        List<TenantSubscriptionBill> subscriptionBillList =  billRepo.findAllByTenant(tenant).orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND,  "bills not found with the given id"));
        log.info("returning the list of bill of a tenant");
        return subscriptionBillList.stream().map(s-> new SubscriptionBillsResponseDto(s.getId(), s.getTenant().getId(), s.getBillStatus(), s.getAmount(), s.getPaidDate(), s.getStartDate(), s.getEndDate())).toList();
    }


    public String payBill(Long id) {
        TenantSubscriptionBill bill = billRepo.findById(id).orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND,  "bill not found with the given id"));
        bill.setBillStatus(BillStatus.PAID);
        billRepo.save(bill);
        log.info("the bill payment is successful");
        return "the bill payment is successful";
    }
}
