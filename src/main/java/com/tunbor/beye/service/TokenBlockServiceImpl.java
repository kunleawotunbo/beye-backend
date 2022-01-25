package com.tunbor.beye.service;

import com.tunbor.beye.entity.TokenBlock;
import com.tunbor.beye.repository.TokenBlockRepository;
import com.tunbor.beye.security.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * @author Olakunle Awotunbo
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class TokenBlockServiceImpl implements TokenBlockService {

    private final TokenBlockRepository tokenBlockRepository;

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public Optional<TokenBlock> findByToken(String token) {
        return tokenBlockRepository.findByToken(token);
    }

    @Override
    public Optional<TokenBlock> findByUserId(Long userId) {
        return tokenBlockRepository.findByUserId(userId);
    }

    @Override
    public TokenBlock save(TokenBlock tokenBlock) {
        return tokenBlockRepository.save(tokenBlock);
    }

    @Override
    public void hardDelete(Long tokenBlockId) {
        tokenBlockRepository.hardDelete(tokenBlockId);
    }

    @Override
    public TokenBlock revokeToken(HttpServletRequest request) {
        String token = jwtTokenUtil.getJwtFromRequest(request);
        Long userId = jwtTokenUtil.getUserIdFromJWT(token);
        TokenBlock tokenBlock = TokenBlock.builder()
                .userId(userId)
                .token(token)
                .blockDate(LocalDateTime.now())
                .build();

        return save(tokenBlock);
    }

    @Override
    public boolean isTokenBlocked(String token) {
        return findByToken(token).isPresent();
    }
}
