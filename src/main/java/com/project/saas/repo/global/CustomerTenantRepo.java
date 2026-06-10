package com.project.saas.repo.global;

import com.project.saas.entity.master.CustomerTenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerTenantRepo extends JpaRepository<CustomerTenant,Integer> {
    Page<CustomerTenant> findAllByArea_Id(Long areaId, Pageable pageable);

    List<CustomerTenant> findAllByCustomer_Id(Long customerId);
}
