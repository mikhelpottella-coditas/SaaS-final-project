package com.project.saas.repo.global;

import com.project.saas.entity.master.Tenant;
import com.project.saas.entity.master.TenantAvailableStates;
import com.project.saas.enums.AvailableState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface TenantAvailableStatesRepo extends JpaRepository<TenantAvailableStates, Long> {
    Optional<List<TenantAvailableStates>> findAllByAvailableState(AvailableState availableState);


    Page<TenantAvailableStates> findAllByAvailableState(AvailableState availableState, Pageable pageable);
}
