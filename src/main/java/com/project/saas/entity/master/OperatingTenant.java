package com.project.saas.entity.master;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "operating_tenant",schema = "public")
public class OperatingTenant {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    @OneToOne
    @JoinColumn(name = "sales_point")
    private User salesPoint;

    @OneToOne
    @JoinColumn(name = "operating_tenant")
    private Tenant tenant;

}
