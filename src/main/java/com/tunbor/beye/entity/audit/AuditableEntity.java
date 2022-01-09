package com.tunbor.beye.entity.audit;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

/**
 * @author Olakunle Awotunbo
 * @since 06/01/2022
 */

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class AuditableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedBy
    private Long createdBy;

    @CreatedDate
    @NotNull
    private Instant createdAt;

    @LastModifiedBy
    @NotNull
    private Long updatedBy;

    @LastModifiedDate
    @NotNull
    private Instant updatedAt;

    @Column(name = "is_active", nullable = false)
    protected boolean active = true;

    @Version
    private int version;

    public String getTableName() {
        return getTableName(getClass());
    }

    public static <T extends AuditableEntity> String getTableName(Class<T> entityClass) {
        Table table = entityClass.getAnnotation(Table.class);
        return table == null ? null : table.name();
    }

    @PrePersist
    protected void prePersist() {
        if (this.createdAt == null)
            createdAt = Instant.now();
    }

    @PreUpdate
    protected void preUpdate() {
        if (this.updatedAt == null)
            updatedAt = Instant.now();
    }
}
