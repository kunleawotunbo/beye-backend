package com.tunbor.beye.entity;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
