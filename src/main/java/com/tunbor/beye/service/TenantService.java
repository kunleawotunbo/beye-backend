package com.tunbor.beye.service;

import com.tunbor.beye.entity.Tenant;

/**
 * @author Olakunle Awotunbo
 * @since 08/01/2022
 */
public interface TenantService {

    Tenant save(Tenant tenant);

    Tenant update(Long id, Tenant tenant);

}
