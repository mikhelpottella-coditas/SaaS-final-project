package com.project.saas.repo.tenant;

import com.project.saas.entity.tenant.TenantMeterPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantMeterPhotoRepo extends JpaRepository<TenantMeterPhoto,Long> {
}
