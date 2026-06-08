package com.project.saas.repo.tenant;

import com.project.saas.entity.tenant.TenantCustomerMeter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantCustomerMeterRepo extends JpaRepository<TenantCustomerMeter,Long> {
}
