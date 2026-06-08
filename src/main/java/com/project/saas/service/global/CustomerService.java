package com.project.saas.service.global;

import com.project.saas.entity.master.Customer;
import com.project.saas.repo.global.CustomerRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final UserService userService;

    public void save(Customer c) {
        customerRepo.save(c);
    }
}
