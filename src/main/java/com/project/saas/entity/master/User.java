package com.project.saas.entity.master;

import com.project.saas.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users",schema = "public")
public class User implements UserDetails{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email",  unique = true, nullable = false)
    private String email;

    @Column(name = "password",  nullable = false)
    private String password;

    @Column(name = "phone", nullable = false,unique = true)
    private String phone;


    private Role role;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne()
    @JoinColumn(name = "tenant_id",referencedColumnName = "id")
    private Tenant tenant;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Crm crm;

    @OneToOne(mappedBy = "electrician")
    private Area electrianAreas;

    @OneToOne(mappedBy = "biller")
    private Area billerAreas;

    @OneToMany(mappedBy = "managerUser")
    private List<Cities> managerCities;

    @OneToMany(mappedBy = "managerUser")
    private List<District> managerDistrict;


    @OneToMany(mappedBy = "managerUser")
    private List<State> managerState;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_"+role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }




}
