package com.project.saas.entity.tenant;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tenant_meter_photo")
public class TenantMeterPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "reference")
    private String reference;

    @Column(name = "capture_time")
    private LocalDateTime captureTime;

    @ManyToOne
    @JoinColumn(name = "bill_id")
    private CustomerBill bill;

}
