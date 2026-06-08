package com.project.saas.repo.tenant;

import com.project.saas.entity.tenant.CustomerBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerBillsRepo extends JpaRepository<CustomerBill,Long> {
}
