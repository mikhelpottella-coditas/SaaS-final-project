package com.project.saas.repo;

import com.project.saas.entity.master.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvitationRepo extends JpaRepository<Invitation, Long> {
    Invitation findByInvitationToken(String token);
}
