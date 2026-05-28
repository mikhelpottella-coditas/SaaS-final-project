package com.project.saas.repo;

import com.project.saas.entity.master.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepo extends JpaRepository<Tenant, Long> {
    boolean existsTenantByName(String name);
}
