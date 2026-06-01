package com.project.saas.entity.tenant;

import com.project.saas.entity.master.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TenantRefreshToken {

    @Id
    private String token;

    @ManyToOne
    private TenantUser tenantUser;

    private LocalDateTime expiryDate;

}
