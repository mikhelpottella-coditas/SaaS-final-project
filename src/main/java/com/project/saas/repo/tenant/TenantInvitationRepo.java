package com.project.saas.repo.tenant;

import com.project.saas.entity.master.Invitation;
import com.project.saas.entity.tenant.TenantInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantInvitationRepo extends JpaRepository<TenantInvitation,Long> {
    Invitation findByInvitationToken(String token);
}
