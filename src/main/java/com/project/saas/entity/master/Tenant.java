package com.project.saas.entity.master;

import com.project.saas.enums.TenantStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tenants",schema = "public")
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false,unique = true)
    private Long id;

    @Column(name = "name",unique = true,nullable = false)
    private String name;

    @Column(name = "schema_name",unique = true,nullable = false)
    private String schemaName;

    @Column(name = "tenant_status",nullable = false)
    @Enumerated(EnumType.STRING)
    private TenantStatus tenantStatus;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;


    @Column(name = "subscription_amount",nullable = false)
    private Double subscriptionAmount;

    @OneToOne(mappedBy = "tenant",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private OperatingTenant operatingTenant;

    @OneToMany(mappedBy = "tenant")
    private List<TenantSubscriptionBill> tenantSubscriptionBillList;

    @OneToMany(mappedBy = "tenant",fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    private List<TenantAvailableStates> tenantAvailableStatesList;

    @OneToMany(mappedBy = "tenant")
    private List<CustomerTenant> customerTenantList;




    public void addState(TenantAvailableStates tenantAvailableStates){
        if(tenantAvailableStatesList==null) tenantAvailableStatesList = new ArrayList<>();
        tenantAvailableStatesList.add(tenantAvailableStates);
        tenantAvailableStates.setTenant(this);
    }


}
