package com.tunbor.beye.service;

import com.tunbor.beye.entity.Company;
import com.tunbor.beye.repository.CompanyRepository;
import com.tunbor.beye.utility.ServiceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Olakunle Awotunbo
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public Company save(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Company update(Long id, Company company) {
        companyRepository.findById(id)
                .orElseThrow(() -> ServiceUtils.wrongIdException("Company", id));

        return companyRepository.save(company);
    }
}
