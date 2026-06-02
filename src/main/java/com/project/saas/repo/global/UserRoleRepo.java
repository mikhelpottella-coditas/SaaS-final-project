package com.project.saas.repo.global;

import com.project.saas.entity.master.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRoles, Long> {
}
