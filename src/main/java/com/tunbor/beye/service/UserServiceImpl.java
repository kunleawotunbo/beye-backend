package com.tunbor.beye.service;

import com.tunbor.beye.entity.Company;
import com.tunbor.beye.entity.Tenant;
import com.tunbor.beye.entity.User;
import com.tunbor.beye.entity.UserRole;
import com.tunbor.beye.entity.enums.Role;
import com.tunbor.beye.payload.LoginRequest;
import com.tunbor.beye.payload.UserTokenResponse;
import com.tunbor.beye.repository.UserRepository;
import com.tunbor.beye.security.jwt.JwtTokenProvider;
import com.tunbor.beye.utility.exception.AppRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * @author Olakunle Awotunbo
 * @since 06/01/2022
 */

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;

    private final UserRepository userRepository;

    private final TenantService tenantService;
    private final CompanyService companyService;

    @Override
    public UserTokenResponse authenticate(LoginRequest loginRequest) {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsernameOrEmail(),
                    loginRequest.getPassword()
            );
            Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenProvider.generateToken(authentication);
            return new UserTokenResponse(jwt, null);
        } catch (BadCredentialsException e) {
            throw new AppRuntimeException("Invalid username/password");
        } catch (DisabledException e) {
            throw new AppRuntimeException(String.format("User %s is Disabled. Please meet with administrator",
                    loginRequest.getUsernameOrEmail()));
        }
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, User user) {
        return null;
    }

    @Override
    public User createTestUser(User user) {
        user.setFirstName(user.getFirstName());
        user.setEmail("test@test.com");

        /*
        userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> ServiceUtils.duplicateEmailException("User", user.getEmail()));
        */

        Tenant tenant = new Tenant();
        tenant.setName("Test Tenant");
        tenant.setCreatedBy(0L);
        tenant.setUpdatedBy(0L);
        // tenant.setCompanies(Collections.singleton(company));

        Tenant newTenant = tenantService.save(tenant);

        Company company = new Company();
        company.setName("Sofort");
        company.setTenant(newTenant);
        company.setCreatedBy(0L);
        company.setUpdatedBy(0L);
        Company newCompany = companyService.save(company);


        UserRole userRole = new UserRole();
        userRole.setRole(Role.ADMIN);

        user.setLastName("Fashola");
        user.setUsername("test");
        user.setPassword("Test");
        user.setCompany(newCompany);
        user.setUserRoles(Collections.singleton(userRole));
        String hash = passwordEncoder.encode(user.getPassword());
        user.setPassword(hash);
        user.setCreatedBy(0L);
        user.setUpdatedBy(0L);
        user.setEnabled(true);

        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

}
