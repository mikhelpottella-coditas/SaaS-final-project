package com.project.saas.repo.tenant;

import com.project.saas.entity.tenant.CustomerBill;
import com.project.saas.entity.tenant.TenantCustomerMeter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerBillsRepo extends JpaRepository<CustomerBill,Long> {
    Optional<CustomerBill> findByTenantCustomerMeter(TenantCustomerMeter tenantCustomerMeter);

    List<CustomerBill> findAllByTenantCustomerMeter(TenantCustomerMeter tenantCustomerMeter);
}
