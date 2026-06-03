package com.project.saas.repo.global;

import com.project.saas.entity.master.Cities;
import com.project.saas.entity.master.District;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepo extends JpaRepository<Cities, Long> {
    Optional<Cities> findCitiesByName(String name);

    Optional<Cities> findCitiesByManagerUser_Id(Long managerUserId);

    Page<Cities> findAllByDistrict(District district, Pageable pageable);
}
