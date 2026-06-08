package com.project.saas.service.tenant;

import com.project.saas.repo.tenant.CustomerBillsRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomerBillService {

    private final CustomerBillsRepo customerBillsRepo;

}
