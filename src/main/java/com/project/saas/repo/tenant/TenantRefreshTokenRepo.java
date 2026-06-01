package com.project.saas.repo.tenant;


import com.project.saas.entity.tenant.TenantRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRefreshTokenRepo extends JpaRepository<TenantRefreshToken,String> {
}
