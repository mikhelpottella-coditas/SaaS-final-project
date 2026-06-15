package com.project.saas.repo.tenant;

import com.project.saas.entity.tenant.TenantStates;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantStateRepo extends JpaRepository<TenantStates, Long> {
    boolean existsByName(String name);

    Optional<TenantStates> findTenantStatesByName(String name);
}
