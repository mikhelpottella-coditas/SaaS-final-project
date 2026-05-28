package com.project.saas.entity.master;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(schema = "public")
public class RefreshToken {

    @Id
    private String token;

    @ManyToOne
    private User user;

    private Instant expiryDate;
}