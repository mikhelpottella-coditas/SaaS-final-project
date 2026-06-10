package com.project.saas.entity.tenant;

import com.project.saas.enums.CustomerBillStatus;
import com.project.saas.enums.PaymentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "paid_date")
    private LocalDateTime paidDate;

    @Column(name = "payment_type")
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(name = "paid_status",nullable = false)
    @Enumerated(EnumType.STRING)
    private CustomerBillStatus billStatus;

    @OneToMany(mappedBy = "bill",cascade = CascadeType.ALL)
    private List<TenantMeterPhoto> meterPhotosList;


    public void addPhoto(TenantMeterPhoto meterPhoto) {
        if(meterPhotosList == null) meterPhotosList = new ArrayList<>();
        meterPhotosList.add(meterPhoto);
        meterPhoto.setBill(this);
    }


}
