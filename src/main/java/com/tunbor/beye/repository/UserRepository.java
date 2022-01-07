package com.tunbor.beye.repository;

import com.tunbor.beye.entity.User;
import com.tunbor.beye.entity.UserRole;
import com.tunbor.beye.repository.base.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author Olakunle Awotunbo
 * @since 06/01/2022
 */
@Repository
public interface UserRepository extends BaseJpaRepository<User> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    List<User> findByUserRolesIn(Set<UserRole> roles);

    @Query(value = "select u.id, u.email, u.name, u.username, u.account_non_expired, u.account_non_locked, u.credentials_non_expired,"
            + "u.enabled, u.district_id, u.created_by, u.updated_by, u.created_at, u.updated_at"
            + " from users u inner join user_roles r "
            + "where u.id = r.user_id and r.roles_id = :roleId",
            nativeQuery = true)
    List<Object[]> findByRoleId(@Param("roleId") Long roleId);

    @Query(value = "select u.id, u.email, u.name from users u inner join user_roles r where u.id = r.user_id and r.roles_id = :roleId",
            nativeQuery = true)
    List<User> findByUserRoleId(@Param("roleId") Long roleId);

    Page<User> findByUserRolesIn(Set<UserRole> roles, Pageable pageable);

    List<User> findByCompanyId(UUID companyId);

    boolean existsByEmailIgnoreCase(String email);

    Optional<User> findFirstByEmailIgnoreCase(String email);
}
