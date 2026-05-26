package com.project.saas.entity.tenant;

import com.project.saas.entity.master.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tenant_customer")
public class TenantCustomer {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "address")
    private String address;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private TenantCity  city;



}
