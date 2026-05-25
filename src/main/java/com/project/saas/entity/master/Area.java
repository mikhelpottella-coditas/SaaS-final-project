package com.project.saas.entity.master;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "areas")
public class Area {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false,unique = true)
    private String name;

    @Column(name = "code",nullable = false)
    private String code;

    @ManyToOne
    @JoinColumn(name = "electrician_id")
    private User electrician;

    @ManyToOne
    @JoinColumn(name = "city_id",nullable = false)
    private Cities city;

    @ManyToOne
    @JoinColumn(name = "biller_id")
    private User biller;







}
