package com.project.saas.entity.master;

import com.project.saas.enums.TenantStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tenants")
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,unique = true)
    private Long id;

    @Column(name = "name",unique = true,nullable = false)
    private String name;

    @Column(name = "schema_name",unique = true,nullable = false)
    private String schemaName;

    @Column(name = "tenant_schema",unique = true,nullable = false)
    @Enumerated(EnumType.STRING)
    private TenantStatus tenantStatus;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;


    @OneToOne
    @JoinColumn(name = "operational_head_id",nullable = false)
    private User operationalHead;

    @OneToOne
    @JoinColumn(name = "point_of_contact_id",nullable = false)
    private User pointOfContact;

    @Column(name = "subscription_amount",nullable = false)
    private Double subscriptionAmount;


    @OneToMany(mappedBy = "tenant")
    private List<Meter> meterList;

    @OneToMany(mappedBy = "tenant")
    private List<TenantSubscriptionBill> tenantSubscriptionBillList;

    @OneToMany(mappedBy = "tenant")
    private List<TenantAvailableStates> tenantAvailableStatesList;

    @OneToMany(mappedBy = "tenant")
    private List<CustomerTenant> customerTenantList;
}
