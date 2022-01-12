package com.tunbor.beye.mapstruct.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * @author Olakunle Awotunbo
 */

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AuditableEntityDTO {

    private Long id;

    private Long createdBy;

    @NotNull
    private Instant createdAt;

    @NotNull
    private Long updatedBy;

    @NotNull
    private Instant updatedAt;

    protected boolean active;
}
