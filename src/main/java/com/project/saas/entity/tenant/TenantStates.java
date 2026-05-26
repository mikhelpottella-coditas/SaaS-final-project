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

    @ManyToOne
    @JoinColumn(name = "m1_manager_id")
    private TenantUser m1Manager;

    @ManyToOne
    @JoinColumn(name = "m2_manager_id")
    private TenantUser m2Manager;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "code",nullable = false)
    private String code;

    @OneToMany(mappedBy = "state")
    private List<TenantDistrict > tenantDistrictList;


}
