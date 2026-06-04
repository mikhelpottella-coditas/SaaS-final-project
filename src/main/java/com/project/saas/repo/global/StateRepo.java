package com.project.saas.repo.global;

import com.project.saas.entity.master.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StateRepo extends JpaRepository<State,Long> {
    Optional<State> findStateByName(String name);

    Optional<State> findStateByManagerUser_Id(Long managerUserId);

    boolean existsByName(String name);
}
