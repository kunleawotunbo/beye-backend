package com.tunbor.beye.service;

import com.tunbor.beye.entity.TokenBlock;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Olakunle Awotunbo
 */
public interface TokenBlockService {

    Optional<TokenBlock> findByToken(String token);

    Optional<TokenBlock> findByUserId(Long userId);

    Optional<TokenBlock> findFirstByUserIdAndToken(Long userId, String token);

    TokenBlock save(TokenBlock tokenBlock);

    void hardDelete(Long tokenBlockId);

    TokenBlock revokeToken(HttpServletRequest request);

    boolean isTokenBlocked(String token);

    List<TokenBlock> findByTokenExpiryDateBefore(Date tokenExpiryDate);

    void deleteTokenBlockByTokenExpiryDateBefore(Date tokenExpiryDate);
}
