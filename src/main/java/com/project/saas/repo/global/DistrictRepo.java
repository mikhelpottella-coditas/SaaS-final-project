package com.project.saas.repo.global;

import com.project.saas.entity.master.District;
import com.project.saas.entity.master.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DistrictRepo extends JpaRepository<District, Long> {
    Optional<District> findDistrictByManagerUser_Id(Long id);

    Page<District> findAllByState(State state, Pageable pageable);

    boolean existsDistrictByName(String districtName);

    Optional<List<District>> findAllByManagerUser_Id(Long id);
}
