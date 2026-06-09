package com.project.saas.service.global;

import com.project.saas.dto.global.responceDto.CustomerResponseDto;
import com.project.saas.entity.master.Customer;
import com.project.saas.entity.master.CustomerTenant;
import com.project.saas.entity.master.Meter;
import com.project.saas.entity.master.Tenant;
import com.project.saas.exception.CustomException;
import com.project.saas.repo.global.CustomerRepo;
import com.project.saas.repo.global.CustomerTenantRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final UserService userService;
    private final CustomerTenantRepo customerTenantRepo;

    public void save(Customer c) {
        customerRepo.save(c);
    }

    public Customer findById(Long customerId) {
        return customerRepo.findById(customerId).orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND, "customer not found with the given id : "+customerId));
    }


    public List<CustomerResponseDto> getByArea(Long areaId, int page, int size, String sortBy, boolean ascending, String search) {
        log.info("start fetching the customer details in a particular area");

        Sort sort = ascending? Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        List<CustomerTenant> customerTenantList = customerTenantRepo.findAllByArea_Id(areaId,pageable).getContent();

        if(customerTenantList.isEmpty()) throw new CustomException(HttpStatus.NOT_FOUND, "customer not found with the given id : "+areaId);

        List<CustomerResponseDto> customerResponseDtoList = new ArrayList<>();

        customerTenantList.forEach(customerTenant -> {
            Customer customer = customerTenant.getCustomer();
            List<Long> areaIds = customer.getCustomerTenantList().stream().map(c->c.getArea().getId()).toList();
            List<Long> tenantIds = customer.getCustomerTenantList().stream().map(c->c.getTenant().getId()).toList();
            List<Long> meterIds = customer.getCustomerTenantList().stream().map(c->c.getMeter().getId()).toList();



            CustomerResponseDto customerResponseDto = new CustomerResponseDto(customer.getId(), customer.getUser().getFirstName(), customer.getUser().getLastName(), customer.getUser().getEmail(), customer.getUser().getPhone(), customer.getUser().getCreatedAt(), customer.getUser().getUpdatedAt(), customer.getAddress(),areaIds , customer.getCrm().getId(),tenantIds,meterIds,customerTenant.isActive());
            customerResponseDtoList.add(customerResponseDto);
        });

        if(search.isEmpty()) return customerResponseDtoList;

        log.info("fetching the customer details in a particular area done");
        return customerResponseDtoList.stream().filter(c->c.firstName().contains(search)).toList();

    }

    public CustomerResponseDto getByCustomerId(Long id) {

        Customer customer = customerRepo.findById(id).orElseThrow(()-> new CustomException(HttpStatus.NOT_FOUND, "customer not found with the given id"));

        if(customer.getCustomerTenantList().isEmpty()) throw new CustomException(HttpStatus.NOT_FOUND, "customer with the given id is not having any meter");
        List<Long> areaIds = customer.getCustomerTenantList().stream().map(c->c.getArea().getId()).toList();
        List<Long> tenantIds = customer.getCustomerTenantList().stream().map(c->c.getTenant().getId()).toList();
        List<Long> meterIds = customer.getCustomerTenantList().stream().map(c->c.getMeter().getId()).toList();

        log.info("customer details are provided with the given id : {}", id);
        return new CustomerResponseDto(customer.getId(), customer.getUser().getFirstName(), customer.getUser().getLastName(), customer.getUser().getEmail(), customer.getUser().getPhone(), customer.getUser().getCreatedAt(), customer.getUser().getUpdatedAt(), customer.getAddress(),areaIds , customer.getCrm().getId(),tenantIds,meterIds,true);
    }
}
