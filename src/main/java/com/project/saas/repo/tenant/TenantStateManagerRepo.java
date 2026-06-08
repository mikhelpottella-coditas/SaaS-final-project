package com.project.saas.repo.tenant;

import com.project.saas.entity.tenant.TenantStateManager;
import com.project.saas.entity.tenant.TenantStates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantStateManagerRepo extends JpaRepository<TenantStateManager,Long> {
    boolean existsByState(TenantStates state);

    TenantStateManager findByState(TenantStates state);
}
