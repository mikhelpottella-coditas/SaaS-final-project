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
@Table(name = "districts",schema = "public")
public class District {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false,unique = true)
    private Long id;

    @Column(name = "code",nullable = false)
    private String code;

    @Column(name = "name",nullable = false)
    private String name;

    @OneToMany(mappedBy = "district",cascade = CascadeType.ALL)
    private List<Cities> citiesList;

    @ManyToOne
    @JoinColumn(name = "manager_user_id")
    private User managerUser;

    @ManyToOne
    @JoinColumn(name = "state_id",nullable = false)
    private State state;

    @OneToMany(mappedBy = "district")
    private List<Cities> cities;

}
