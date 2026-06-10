package com.project.saas.entity.tenant;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tenant_state_manager")
public class TenantStateManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private TenantStates state;

    @ManyToOne
    @JoinColumn(name = "m1_manager_id")
    private TenantUser m1Manager;

    @ManyToOne
    @JoinColumn(name = "m2_manager_id")
    private TenantUser m2Manager;

    @ManyToOne
    @JoinColumn(name = "personnel_id")
    private TenantUser personnel;

}
