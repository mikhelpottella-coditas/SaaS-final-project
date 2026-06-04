package com.project.saas.entity.master;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers",schema = "public")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;



    @Column(name = "address",nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "area_id",nullable = false)
    private Area area;

    @ManyToOne
    private Crm crm;

    @OneToMany(mappedBy = "customer")
    private List<CustomerTenant> customerTenantList;

}
