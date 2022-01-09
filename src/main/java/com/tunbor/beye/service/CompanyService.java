package com.tunbor.beye.service;

import com.tunbor.beye.entity.Company;

/**
 * @author Olakunle Awotunbo
 */
public interface CompanyService {

    Company save(Company company);

    Company update(Long id, Company company);

}
