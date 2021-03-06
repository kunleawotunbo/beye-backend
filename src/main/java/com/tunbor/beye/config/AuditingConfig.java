package com.tunbor.beye.config;

import com.tunbor.beye.security.AppUserDetails;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Olakunle Awotunbo
 */

@Configuration
@EnableJpaAuditing
public class AuditingConfig implements AuditorAware<Long> {
    @Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();

        return Optional.ofNullable(appUserDetails.getUserId());
    }
}
