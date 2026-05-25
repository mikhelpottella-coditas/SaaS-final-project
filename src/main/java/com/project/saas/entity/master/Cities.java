package com.project.saas.entity.master;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cities")
public class Cities {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true,nullable = false)
    private Long id;

    @Column(name = "code",nullable = false)
    private String code;

    @Column(name = "name",nullable = false)
    private String name;

    @OneToMany(mappedBy = "city",cascade = CascadeType.ALL)
    private List<Area> areaList;

    @ManyToOne
    @JoinColumn(name = "manager_user_id")
    private User managerUser;

    @ManyToOne
    @JoinColumn(name = "district_service_id",nullable = false)
    private District district;


}
