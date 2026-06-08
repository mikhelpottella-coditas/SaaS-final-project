package com.project.saas.repo.tenant;

import com.project.saas.entity.tenant.TenantCustomerMeter;
import com.project.saas.entity.tenant.TenantStates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TenantCustomerMeterRepo extends JpaRepository<TenantCustomerMeter,Long> {
    List<TenantCustomerMeter> findAllByTenantStates(TenantStates tenantStates);
}
