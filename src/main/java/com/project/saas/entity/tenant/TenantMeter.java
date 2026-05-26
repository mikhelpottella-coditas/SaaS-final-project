package com.project.saas.entity.tenant;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tenant_meters")
public class TenantMeter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type",nullable = false)
    private String type;

    @Column(name = "rate_per_unit",nullable = false)
    private Double ratePerUnit;

    @Column(name = "Photos_required", nullable = false)
    private Integer photosRequired;

    @Column(name = "interval_btw_photos",nullable = false)
    private Integer intervalBtwPhotos;

}
