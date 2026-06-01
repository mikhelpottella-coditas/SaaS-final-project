package com.project.saas.repo;

import com.project.saas.entity.master.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TenantRepo extends JpaRepository<Tenant, Long> {
    boolean existsTenantByName(String name);



    Optional<Tenant> findTenantByName(String tenant);

    List<Tenant> findTenantByOperatingTenant_SalesPoint_Id(Long id);
}
