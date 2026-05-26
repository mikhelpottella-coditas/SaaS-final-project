package com.project.saas.entity.tenant;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "city_personnel")
public class CityPersonnel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private TenantCity user;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private TenantCity tenantCity;

}
