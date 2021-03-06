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
@Table(name = "tenant")
public class Tenant extends AuditableEntity {

    @NotBlank
    @Size(max = 100)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy="tenant")
    private Set<Company> companies;
}
