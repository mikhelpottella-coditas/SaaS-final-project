package com.project.saas.entity.tenant;

import com.project.saas.entity.master.UserRoles;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class TenantUser {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email",  unique = true, nullable = false)
    private String email;


    @Column(name = "phone", nullable = false,unique = true)
    private String phone;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
    private List<TenantUserRoles> tenantUserRolesList;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}

