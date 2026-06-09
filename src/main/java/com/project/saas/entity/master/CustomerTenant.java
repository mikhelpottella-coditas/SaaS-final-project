package com.project.saas.entity.master;

import jakarta.mail.Address;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_tenant",schema = "public")
public class CustomerTenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "customer_id",nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "tenant_id",nullable = false)
    private Tenant tenant;
    

    @ManyToOne
    @JoinColumn(name = "area_id",nullable = false)
    private Area area;

    @Column(name = "is_active",nullable = false)
    private boolean isActive;




}
