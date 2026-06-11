package com.project.saas.repo.tenant;


import com.project.saas.entity.master.User;
import com.project.saas.entity.tenant.TenantRefreshToken;
import com.project.saas.entity.tenant.TenantUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRefreshTokenRepo extends JpaRepository<TenantRefreshToken,String> {
    Optional<TenantRefreshToken> findByTenantUser(TenantUser user);
}
