package com.tunbor.beye.service;

import com.tunbor.beye.entity.Company;
import com.tunbor.beye.entity.Tenant;
import com.tunbor.beye.entity.User;
import com.tunbor.beye.entity.UserRole;
import com.tunbor.beye.entity.enums.Role;
import com.tunbor.beye.mapstruct.dto.UserGetDTO;
import com.tunbor.beye.payload.LoginRequest;
import com.tunbor.beye.payload.RefreshTokenRequest;
import com.tunbor.beye.payload.UserTokenResponse;
import com.tunbor.beye.repository.CompanyRepository;
import com.tunbor.beye.repository.TenantRepository;
import com.tunbor.beye.repository.UserRepository;
import com.tunbor.beye.security.AppUserDetails;
import com.tunbor.beye.security.jwt.JwtTokenUtil;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.tunbor.beye.mapstruct.mappers.UserMapper.*;

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
    private final JwtTokenUtil jwtTokenUtil;

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final CompanyRepository companyRepository;

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

            String jwt = jwtTokenUtil.generateToken(authentication);
            final String refreshToken = jwtTokenUtil.generateRefreshToken(loginRequest.getClientId());
            return new UserTokenResponse(jwt, refreshToken);
        } catch (BadCredentialsException e) {
            throw new AppRuntimeException("Invalid username/password");
        } catch (DisabledException e) {
            throw new AppRuntimeException(String.format("User %s is Disabled. Please meet with administrator",
                    loginRequest.getUsernameOrEmail()));
        }
    }

    @Override
    public UserTokenResponse refreshToken(RefreshTokenRequest request) {
        AppUserDetails userDetails = jwtTokenUtil.getUserDetailsFromToken(request.getToken());

        String token = jwtTokenUtil.generateAuthToken(userDetails);
        String refreshToken = getRefreshToken(request);

        return new UserTokenResponse(token, refreshToken);
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

        /*
        userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> ServiceUtils.duplicateEmailException("User", user.getEmail()));
        */

        Tenant tenant;
        Optional<Tenant> firstTenant = tenantRepository.findById(1L);
        if (firstTenant.isPresent()) {
            tenant = firstTenant.get();
        } else {
            tenant = new Tenant();
            tenant.setName("Test Tenant");
            tenant.setCreatedBy(0L);
            tenant.setUpdatedBy(0L);

            tenant = tenantService.save(tenant);
        }

        Company company;
        Optional<Company> firstCompany = companyRepository.findById(1L);
        if (firstCompany.isPresent()) {
            company = firstCompany.get();
        } else {
            company = new Company();
            company.setName("Sofort");
            company.setTenant(tenant);
            company.setCreatedBy(0L);
            company.setUpdatedBy(0L);

            company = companyService.save(company);
        }

        String firstNameLastName = user.getFirstName().toLowerCase() + "." + user.getLastName().toLowerCase();

        UserRole userRole = new UserRole();
        userRole.setRole(Role.ADMIN);

        user.setEmail(firstNameLastName + "@test.com");
        user.setUsername(firstNameLastName);
        user.setPassword("Test1");
        user.setCompany(company);
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

    @Override
    public List<UserGetDTO> findAllDTO() {
        List<UserGetDTO> userGetDTOs = new ArrayList<>();
        userRepository.findAll().forEach(user -> userGetDTOs.add(INSTANCE.userToUserGetDTO(user)));
        return userGetDTOs;
    }

    private String getRefreshToken(RefreshTokenRequest request) {
        String clientId = jwtTokenUtil.getSubjectFromToken(request.getRefreshToken());

        // clientService.validateClient(clientId, request.getClientSecret());

        return jwtTokenUtil.isTokenExpired(request.getRefreshToken()) ?
                jwtTokenUtil.generateRefreshToken(clientId) :
                request.getRefreshToken();
    }

}
