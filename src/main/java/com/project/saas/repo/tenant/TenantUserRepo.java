package com.project.saas.repo.tenant;

import com.project.saas.entity.tenant.TenantUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantUserRepo extends JpaRepository<TenantUser,Long> {
    TenantUser findTenantUserByEmail(String username);
}
