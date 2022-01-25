package com.tunbor.beye.repository;

import com.tunbor.beye.entity.TokenBlock;
import com.tunbor.beye.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Olakunle Awotunbo
 */
@Repository
public interface TokenBlockRepository extends BaseJpaRepository<TokenBlock> {

    Optional<TokenBlock> findByToken(String token);

    Optional<TokenBlock> findByUserId(Long userId);

    List<TokenBlock> findByBlockDateBefore(LocalDateTime blockDate);

    @Modifying
    @Query("delete from TokenBlock t where t.blockDate < ?1")
    void deleteTokenBlockByBlockDateBefore(LocalDateTime blockDate);
}
