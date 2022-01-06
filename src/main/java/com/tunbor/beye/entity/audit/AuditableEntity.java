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
import java.util.UUID;

/**
 * @author Olakunle Awotunbo
 * @since 06/01/2022
 */

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class AuditableEntity implements Serializable {

    // @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    @CreatedBy
    @Column(columnDefinition = "BINARY(16)")
    private UUID createdBy;

    @CreatedDate
    @NotNull
    private Instant createdAt;

    @LastModifiedBy
    @NotNull
    @Column(columnDefinition = "BINARY(16)")
    private UUID updatedBy;

    @LastModifiedDate
    @NotNull
    private Instant updatedAt;

    @Column(name = "is_active", nullable = false)
    protected boolean active = true;

    @Version
    private int version;


    @PrePersist
    public void validateUuid() {
        if (uuid == null)
            uuid = UUID.randomUUID();
    }


    public String getTableName() {
        return getTableName(getClass());
    }


    public static <T extends AuditableEntity> String getTableName(Class<T> entityClass) {
        Table table = entityClass.getAnnotation(Table.class);
        return table == null ? null : table.name();
    }


    @Override
    public boolean equals(Object obj) {
        if (uuid == null)
            return super.equals(obj);
        else if (obj == null || obj.getClass() != getClass())
            return false;

        return uuid == ((AuditableEntity) obj).uuid;
    }


    @Override
    public int hashCode() {
        if (uuid == null)
            return super.hashCode();

        long bitSum = uuid.getMostSignificantBits() + uuid.getLeastSignificantBits();
        return Long.valueOf(bitSum).intValue();
    }
}
