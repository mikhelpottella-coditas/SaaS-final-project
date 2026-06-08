package com.project.saas.service.global;

import com.project.saas.entity.master.AssignWork;
import com.project.saas.repo.global.AssignWorkRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AssignWorkService {

    private final AssignWorkRepo assignWorkRepo;


    public void save(AssignWork assignWork) {
        assignWorkRepo.save(assignWork);
    }
}
