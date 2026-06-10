package com.project.saas.repo.global;

import com.project.saas.entity.master.Tenant;
import com.project.saas.entity.master.TenantSubscriptionBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.ScopedValue;
import java.util.List;
import java.util.Optional;

@Repository
public interface TenantSubscriptionBillRepo extends JpaRepository<TenantSubscriptionBill,Long> {
    Optional<TenantSubscriptionBill> findByTenant(Tenant tenant);

    Optional<List<TenantSubscriptionBill>> findAllByTenant(Tenant tenant);
}
