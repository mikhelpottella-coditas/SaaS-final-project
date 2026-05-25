package com.project.saas.repo;

import com.project.saas.entity.master.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {


    User findByEmail(String username);
}