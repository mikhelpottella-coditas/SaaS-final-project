package com.project.saas.entity.tenant;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tenant_cities")
public class TenantCity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "code",nullable = false)
    private String code;

    @ManyToOne
    @JoinColumn(name = "personnel_id")
    private TenantUser  personnel;


    @ManyToOne
    @JoinColumn(name = "district_id")
    private TenantDistrict district;

}
