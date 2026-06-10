package com.project.saas.repo.global;

import com.project.saas.entity.master.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Page<Customer> findAllByArea_Id(Long areaId, Pageable pageable);
}
