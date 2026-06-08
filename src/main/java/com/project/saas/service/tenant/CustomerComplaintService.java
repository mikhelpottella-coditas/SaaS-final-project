package com.project.saas.service.tenant;


import com.project.saas.repo.tenant.CustomerComplaintsRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomerComplaintService {

    private final CustomerComplaintsRepo customerComplaintsRepo;



}
