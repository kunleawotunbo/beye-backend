package com.tunbor.beye.service;

import com.tunbor.beye.entity.TokenBlock;
import com.tunbor.beye.repository.TokenBlockRepository;
import com.tunbor.beye.security.jwt.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Olakunle Awotunbo
 */
@Service
@Slf4j
@Transactional
public class TokenBlockServiceImpl implements TokenBlockService {

    private final TokenBlockRepository tokenBlockRepository;

    private final JwtTokenUtil jwtTokenUtil;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TokenBlockServiceImpl(TokenBlockRepository tokenBlockRepository,
                                 JwtTokenUtil jwtTokenUtil,
                                 @Lazy PasswordEncoder passwordEncoder) {
        this.tokenBlockRepository = tokenBlockRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<TokenBlock> findByToken(String token) {
        return tokenBlockRepository.findByToken(token);
    }

    @Override
    public Optional<TokenBlock> findByUserId(Long userId) {
        return tokenBlockRepository.findByUserId(userId);
    }

    @Override
    public Optional<TokenBlock> findFirstByUserIdAndToken(Long userId, String token) {
        return tokenBlockRepository.findFirstByUserIdAndToken(userId, token);
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
        Date tokenExpiryDate = jwtTokenUtil.getExpirationDateFromToken(token);
        // TODO: Don't store plain token in db
//        String hashedToken = passwordEncoder.encode(token);
        TokenBlock tokenBlock = TokenBlock.builder()
                .userId(userId)
                .token(token)
                .tokenExpiryDate(tokenExpiryDate)
                .build();

        return save(tokenBlock);
    }

    @Override
    public boolean isTokenBlocked(String token) {
        /* TODO: Don't store plain token in db
        Long userId = jwtTokenUtil.getUserIdFromJWT(token);
        String hashedToken = passwordEncoder.encode(token);
        Optional<TokenBlock> firstByUserIdAndToken = findFirstByUserIdAndToken(userId, hashedToken);

        if (firstByUserIdAndToken.isPresent()) {
            if (!passwordEncoder.matches(token, firstByUserIdAndToken.get().getToken()))
                throw new AppRuntimeException("Invalid token");

            return findByToken(firstByUserIdAndToken.get().getToken()).isPresent();
        }

        return false;
        */

        return findByToken(token).isPresent();
    }

    @Override
    public List<TokenBlock> findByTokenExpiryDateBefore(Date tokenExpiryDate) {
        return tokenBlockRepository.findByTokenExpiryDateBefore(tokenExpiryDate);
    }

    @Override
    public void deleteTokenBlockByTokenExpiryDateBefore(Date tokenExpiryDate) {
        tokenBlockRepository.deleteTokenBlockByTokenExpiryDateBefore(tokenExpiryDate);
    }
}
