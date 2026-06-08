package com.project.saas.entity.tenant;


import com.project.saas.entity.master.Customer;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer_meter")
public class TenantCustomerMeter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meter_id")
    private TenantMeter tenantMeter;


    @Column(name = "customer_id", nullable = false)
    private Long customerId;


    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email",  unique = true, nullable = false)
    private String email;

    @Column(name = "phone", nullable = false,unique = true)
    private String phone;

    @Column(name = "address",nullable = false)
    private String address;


    @OneToMany(mappedBy = "tenantCustomerMeter", fetch = FetchType.LAZY)
    private List<CustomerBill> customerBillList;

}
