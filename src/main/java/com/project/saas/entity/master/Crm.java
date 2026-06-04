package com.project.saas.entity.master;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "crm", schema = "public")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Crm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "cities_id")
    private Cities cities;

    @OneToMany(mappedBy = "crm")
    private List<Customer> customer;
}
