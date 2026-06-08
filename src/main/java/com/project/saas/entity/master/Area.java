package com.project.saas.entity.master;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "areas",schema = "public")
public class Area {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false,unique = true)
    private String name;

    @Column(name = "code",nullable = false)
    private String code;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "electrician_id")
    private User electrician;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id",nullable = false)
    private Cities city;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "biller_id")
    private User biller;

    @OneToMany(mappedBy = "area", fetch = FetchType.LAZY)
    private List<Customer> customerList;

}
