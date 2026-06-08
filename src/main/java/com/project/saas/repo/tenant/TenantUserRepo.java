package com.project.saas.repo.tenant;

import com.project.saas.entity.master.User;
import com.project.saas.entity.tenant.TenantUser;
import com.project.saas.enums.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TenantUserRepo extends JpaRepository<TenantUser,Long> {
    TenantUser findTenantUserByEmail(String username);

     List<TenantUser> getByRole(Role role, Pageable pageable);
}
