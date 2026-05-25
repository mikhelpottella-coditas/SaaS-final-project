package com.project.saas.entity.master;

import com.project.saas.enums.BillStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tenant_subscription_bills")
public class TenantSubscriptionBill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false,unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Column(name = "bill_status",nullable = false)
    @Enumerated(EnumType.STRING)
    private BillStatus billStatus;

    @Column(name = "amount_paid",nullable = false)
    private Double amountPaid;

    @Column(name = "paid_date",nullable = false)
    private LocalDate paidDate;

    @Column(name = "start_date",nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date",nullable = false)
    private LocalDate endDate;


}

