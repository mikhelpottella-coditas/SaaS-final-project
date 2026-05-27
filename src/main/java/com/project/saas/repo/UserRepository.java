package com.project.saas.repo;

import com.project.saas.entity.master.User;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String username);


}