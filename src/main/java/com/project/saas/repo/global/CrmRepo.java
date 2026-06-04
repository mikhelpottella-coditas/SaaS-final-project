package com.project.saas.repo.global;

import com.project.saas.entity.master.Crm;
import com.project.saas.entity.master.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrmRepo extends JpaRepository<Crm,Long>
{

    boolean existsCrmByUser(User user);
}
