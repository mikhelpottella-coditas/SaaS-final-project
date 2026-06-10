package com.project.saas.entity.tenant;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tenant_states")
public class TenantStates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "code",nullable = false)
    private String code;

    @OneToMany(mappedBy = "state")
    private List<TenantStateManager>  tenantStateManagerList;

    @OneToMany(mappedBy = "tenantStates")
    private List<TenantCustomerMeter>  tenantCustomerMeterList;

}
