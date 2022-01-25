package com.tunbor.beye.entity;

import com.tunbor.beye.entity.audit.AuditableEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "token_block")
public class TokenBlock extends AuditableEntity {

    @Column(nullable = false)
    private Long userId;

    @NotBlank
    private String token;

    @NotNull
    private LocalDateTime blockDate;
}
