package com.tunbor.beye.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tunbor.beye.entity.audit.AuditableEntity;
import com.tunbor.beye.entity.enums.Role;
import lombok.*;

import javax.persistence.*;

/**
 * @author Olakunle
 * @since 06/01/2022
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_roles")
public class UserRole extends AuditableEntity {

    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
