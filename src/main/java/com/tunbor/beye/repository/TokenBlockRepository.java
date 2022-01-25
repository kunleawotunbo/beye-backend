package com.tunbor.beye.repository;

import com.tunbor.beye.entity.TokenBlock;
import com.tunbor.beye.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Olakunle Awotunbo
 */
@Repository
public interface TokenBlockRepository extends BaseJpaRepository<TokenBlock> {

    Optional<TokenBlock> findByToken(String token);

    Optional<TokenBlock> findByUserId(Long userId);

    Optional<TokenBlock> findFirstByUserIdAndToken(Long userId, String token);

    List<TokenBlock> findByTokenExpiryDateBefore(Date tokenExpiryDate);

    @Modifying
    @Query("delete from TokenBlock t where t.tokenExpiryDate < ?1")
    void deleteTokenBlockByTokenExpiryDateBefore(Date tokenExpiryDate);
}
