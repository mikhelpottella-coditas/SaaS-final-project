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
@Table(name = "tenant_districts")
public class TenantDistrict {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "code",nullable = false)
    private String code;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private TenantStates state;

    @OneToMany(mappedBy = "district")
    private List<TenantCity> tenantCityList;

}
