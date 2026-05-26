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
@Table(name = "tenant_cities")
public class TenantCity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "code",nullable = false)
    private String code;

    @OneToMany(mappedBy = "city")
    private List<TenantCustomer> tenantCustomerList;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private TenantDistrict district;

    @OneToMany(mappedBy = "tenantCity")
    private List<CityPersonnel> cityPersonnelList;

}
