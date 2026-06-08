package com.project.saas.repo.global;

import com.project.saas.entity.master.AssignWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignWorkRepo extends JpaRepository<AssignWork,Long> {
}
