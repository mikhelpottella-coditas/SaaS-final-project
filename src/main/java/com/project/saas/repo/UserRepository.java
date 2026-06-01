package com.project.saas.repo;

import com.project.saas.entity.master.User;
import com.project.saas.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String username);


    Page<User> findUsersByUserRoles(Role role, Pageable pageable);



}