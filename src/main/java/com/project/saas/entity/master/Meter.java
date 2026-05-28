package com.project.saas.entity.master;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "meters",schema = "public")
public class Meter {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,unique = true)
    private Long id;

    @Column(name = "type",nullable = false)
    private String type;

    @Column(name = "rate_per_unit",nullable = false)
    private Double ratePerUnit;

    @Column(name = "photos_required",nullable = false)
    private Integer photosRequired;

    @Column(name = "interval_btw_photos",nullable = false)
    private Integer intervalBtwPhotos;


    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

}
