package com.project.saas.repo.global;

import com.project.saas.entity.master.Area;
import com.project.saas.enums.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AreaRepo extends JpaRepository<Area,Long> {
    Optional<List<Area>> findAllByCity_Id(Long cityId, Pageable pageable);
}
