package com.project.saas.repo.tenant;

import com.project.saas.entity.tenant.TenantMeter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantMeterRepo extends JpaRepository<TenantMeter,Long > {



}
