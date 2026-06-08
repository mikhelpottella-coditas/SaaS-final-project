package com.project.saas.repo.tenant;

import com.project.saas.entity.tenant.CustomerComplaints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerComplaintsRepo extends JpaRepository<CustomerComplaints,Long> {
}
