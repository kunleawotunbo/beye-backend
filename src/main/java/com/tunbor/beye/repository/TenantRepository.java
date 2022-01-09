package com.tunbor.beye.repository;

import com.tunbor.beye.entity.Tenant;
import com.tunbor.beye.repository.base.BaseJpaRepository;

import java.util.Optional;

/**
 * @author Olakunle  Awotunbo
 * @since 08/01/2022
 */
public interface TenantRepository extends BaseJpaRepository<Tenant> {

    Optional<Tenant> findFirstByNameIgnoreCase(String name);
    Optional<Tenant> findById(Long id);
    boolean existsByName(String name);
}
