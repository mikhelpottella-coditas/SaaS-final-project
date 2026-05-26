package com.project.saas.entity.tenant;

import com.project.saas.entity.master.Customer;
import com.project.saas.enums.CustomerBillStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JoinColumnOrFormula;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bills")
public class CustomerBill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_meter_id")
    private TenantCustomerMeter  tenantCustomerMeter;

    @Column(name = "units",nullable = false)
    private Long units;

    @Column(name = "price",nullable = false)
    private Double price;

    @Column(name = "from",nullable = false)
    private LocalDateTime from;

    @Column(name = "to", nullable = false)
    private LocalDateTime to;

    @Column(name = "paid_status",nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerBillStatus billStatus;



}
