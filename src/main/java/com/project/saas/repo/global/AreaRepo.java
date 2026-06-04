package com.project.saas.repo.global;

import com.project.saas.entity.master.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepo extends JpaRepository<Area,Long> {
}
