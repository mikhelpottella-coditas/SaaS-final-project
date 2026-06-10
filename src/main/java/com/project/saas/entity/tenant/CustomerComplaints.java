package com.project.saas.entity.tenant;

import com.project.saas.entity.master.Customer;
import com.project.saas.enums.ComplaintStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer_complaints")
public class CustomerComplaints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_meter_id",nullable = false)
    private TenantCustomerMeter tenantCustomerMeter;

    @Column(name = "complaint",nullable = false)
    private String complaint;

    @Column(name = "complaint_status")
    @Enumerated(EnumType.STRING)
    private ComplaintStatus complaintStatus;

    @Column(name = "raise date",nullable = false)
    private LocalDateTime raiseDate;

    @Column(name = "assigned_electrician")
    private Long assignedElectrician;

}
