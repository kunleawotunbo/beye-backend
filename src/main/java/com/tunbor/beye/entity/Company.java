package com.tunbor.beye.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tunbor.beye.entity.audit.AuditableEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author Olakunle Awotunbo
 * @since 06/01/2022
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "company")
public class Company extends AuditableEntity {
    
    @NotBlank
    @Size(max = 100)
    private String name;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @JsonIgnore
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private Set<User> users;
}
