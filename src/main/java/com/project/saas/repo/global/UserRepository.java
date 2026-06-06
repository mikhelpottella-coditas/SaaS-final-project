package com.project.saas.repo.global;

import com.project.saas.entity.master.District;
import com.project.saas.entity.master.User;
import com.project.saas.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.net.ContentHandler;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByEmail(String username);




    List<User> findAllByRoleAndManagerDistrict_Empty(Role role, List<District> managerDistrict);

    Page<User> findAllByRoleAndManagerDistrictIsEmpty(Role role, Pageable pageable);

    Page<User> findAllByRoleAndManagerCitiesIsEmpty(Role role, Pageable pageable);

    Page<User> findAllByRole(Role role, Pageable pageable);

    Page<User> findAllByRoleAndManagerCitiesIsNotEmpty(Role role, Pageable pageable);

    User findUserByEmail(String username);
}