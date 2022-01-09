package com.tunbor.beye.service;

import com.tunbor.beye.entity.Tenant;
import com.tunbor.beye.repository.TenantRepository;
import com.tunbor.beye.utility.ServiceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Olakunle Awotunbo
 * @since 08/01/2022
 */

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;

    @Override
    public Tenant save(Tenant tenant) {
        if (tenantRepository.existsByName(tenant.getName()))
            ServiceUtils.throwDuplicateNameException("Tenant", tenant.getName());

        return tenantRepository.save(tenant);
    }

    @Override
    public Tenant update(Long id, Tenant tenant) {
        tenantRepository.findById(id)
                .orElseThrow(() -> ServiceUtils.wrongIdException("Tenant", id));

        return tenantRepository.save(tenant);
    }
}
