package com.tunbor.beye.repository.base;

import com.tunbor.beye.entity.audit.AuditableEntity;
import com.tunbor.beye.utility.ServiceUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author Olakunle Awotunbo
 * @since 06/01/2022
 */
@NoRepositoryBean
public interface BaseJpaRepository<TEntity extends AuditableEntity> extends JpaRepository<TEntity, UUID> {

    @Transactional(readOnly = true)
    default TEntity findById(UUID id, String modelName) {
        return findById(id)
                .orElseThrow(() -> ServiceUtils.wrongIdException(modelName, id));
    }


    @Override
    @Transactional
    default void delete(@NotNull TEntity entity) {
        entity.setActive(false);
        save(entity);
    }


    @Override
    @Transactional
    default void deleteById(@NotNull UUID id) {
        TEntity entity = findById(id)
                .orElseThrow(() -> ServiceUtils.wrongIdException("Record", id));
        delete(entity);
    }


    @Override
    @Transactional
    default void deleteAll(@NotNull Iterable<? extends TEntity> entities) {
        for (TEntity entity : entities)
            delete(entity);
    }


    @Override
    @Transactional
    default void deleteAll() {
        deleteAll(findAll());
    }


    @Transactional
    @Modifying
    @Query("delete from #{#entityName} e where e.id = ?1")
    void hardDelete(UUID id);
}
