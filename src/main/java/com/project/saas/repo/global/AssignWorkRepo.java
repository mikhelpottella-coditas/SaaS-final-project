package com.project.saas.repo.global;

import com.project.saas.entity.master.AssignWork;
import com.project.saas.enums.ComplaintStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignWorkRepo extends JpaRepository<AssignWork,Long> {

    List<AssignWork> findAllByAssignedElectrician_Id(Long empId);

    Page<AssignWork> findAllByAssignedElectrician_IdAndComplaintStatus(Long assignedElectricianId, ComplaintStatus complaintStatus, Pageable pageable);
}
