package com.project.saas.service;

import com.project.saas.config.tenantConfig.TenantContext;
import com.project.saas.repo.global.UserRepository;
import com.project.saas.repo.tenant.TenantUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final TenantUserRepo tenantUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (TenantContext.getTenant() == null) return userRepository.findByEmail(username);
        else if (TenantContext.getTenant().equals("public")) return userRepository.findByEmail(username);
        else return tenantUserRepo.findTenantUserByEmail(username);
    }
}
