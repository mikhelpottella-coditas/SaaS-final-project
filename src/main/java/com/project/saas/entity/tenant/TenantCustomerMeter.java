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

    @ManyToOne
    @JoinColumn(name = "meter_id")
    private TenantMeter tenantMeter;


    @Column(name = "customer_id", nullable = false)
    private Long customerId;


    @OneToMany(mappedBy = "tenantCustomerMeter")
    private List<CustomerBill> customerBillList;

}
