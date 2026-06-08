package com.project.saas.repo.tenant;

import com.project.saas.entity.tenant.TenantStateManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantStateManagerRepo extends JpaRepository<TenantStateManager,Long> {
}
