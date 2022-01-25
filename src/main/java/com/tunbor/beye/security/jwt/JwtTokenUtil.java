package com.tunbor.beye.security.jwt;

import com.tunbor.beye.entity.enums.Role;
import com.tunbor.beye.security.AppAuthority;
import com.tunbor.beye.security.AppUserDetails;
import com.tunbor.beye.service.TokenBlockService;
import com.tunbor.beye.utility.exception.AppRuntimeException;
import io.jsonwebtoken.*;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Olakunle Awotunbo
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenUtil implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final long serialVersionUID = -2550185165626007488L;

    private static final long AUTH_TOKEN_TIMEOUT_IN_SECONDS = 24 * 60 * 60; //1 day

    private static final long REFRESH_TOKEN_TIMEOUT_IN_SECONDS = 365 * 24 * 60 * 60; //1 year

    private final String userIdKey = "user_id";

    private final String rolesKey = "roles";

    private final String authorityKey = "authorities";

    private final String clientIdKey = "client_id";

    // private final ClientService clientService;

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {

        AppUserDetails appUserDetails = (AppUserDetails) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setId(appUserDetails.getUserId().toString())
                .setSubject(appUserDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getId());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }

    public String getSubjectFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }


    @SuppressWarnings("unchecked")
    public AppUserDetails getUserDetailsFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);

        Long userId = claims.get(userIdKey, Long.class);
        String username = getSubjectFromToken(token);
        List<Role> roles = claims.get(rolesKey, ArrayList.class);
        List<String> authorityValues = claims.get(authorityKey, ArrayList.class);
        Set<GrantedAuthority> authorities = authorityValues.stream().map(AppAuthority::new)
                .collect(Collectors.toSet());

        return AppUserDetails.builder()
                .userId(userId)
                .username(username)
                .roles(roles)
                .authorities(authorities)
                .enabled(true)
                .build();
    }


    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


    public String generateAuthToken(AppUserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(userIdKey, userDetails.getUserId());
        claims.put(rolesKey, userDetails.getRoles());
        claims.put(authorityKey, userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet()));

        return doGenerateToken(claims, userDetails.getUsername(), AUTH_TOKEN_TIMEOUT_IN_SECONDS);
    }


    public String generateRefreshToken(String clientId) {
        // clientService.validateClientId(clientId);

        Map<String, Object> claims = new HashMap<>();
        claims.put(clientIdKey, clientId);

        return doGenerateToken(claims, clientId, REFRESH_TOKEN_TIMEOUT_IN_SECONDS);
    }


    public Boolean validateToken(String token, String sentUsername) {
        final String username = getSubjectFromToken(token);
        return (username.equals(sentUsername) && !isTokenExpired(token));
    }


    private Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }


    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }


    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException ex) {
            return ex.getClaims();
        }
    }


    private String doGenerateToken(Map<String, Object> claims, String subject, long expiryInSeconds) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiryInSeconds * 1000))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
