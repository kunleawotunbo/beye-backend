package com.tunbor.beye.repository;

import com.tunbor.beye.entity.TokenBlock;
import com.tunbor.beye.repository.base.BaseJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Olakunle Awotunbo
 */
@Repository
public interface TokenBlockRepository extends BaseJpaRepository<TokenBlock> {

    Optional<TokenBlock> findByToken(String token);

    Optional<TokenBlock> findByUserId(Long userId);

}
