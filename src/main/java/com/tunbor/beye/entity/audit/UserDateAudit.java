package com.tunbor.beye.entity.audit;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * @author Olakunle Awotunbo
 * @since 06/01/2022
 */
@MappedSuperclass
@Data
public abstract class UserDateAudit extends DateAudit {

    private UUID id;

    @CreatedBy
    @Column(updatable = false)
    private Long createdBy;

    @LastModifiedBy
    private Long updatedBy;
}
