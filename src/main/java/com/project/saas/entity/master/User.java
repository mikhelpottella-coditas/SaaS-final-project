package com.project.saas.entity.master;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
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

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "user",fetch = FetchType.EAGER)
    private List<UserRoles> userRoles;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id",referencedColumnName = "id")
    private Tenant tenant;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return userRoles.stream().map(u-> new SimpleGrantedAuthority("ROLE_"+u.getRole().name())).toList();
    }

    @Override
    public String getUsername() {
        return email;
    }


    public void addUserRole(UserRoles userRole){
        if(userRoles == null) userRoles = new ArrayList<>();
        userRoles.add(userRole);
        userRole.setUser(this);
    }

}
