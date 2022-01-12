package com.tunbor.beye.mapstruct.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tunbor.beye.entity.Company;
import com.tunbor.beye.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

/**
 * @author Olakunle Awotunbo
 */

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserGetDTO extends AuditableEntityDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String email;

    @JsonIgnore
    private Company company;
    private Set<UserRole> userRoles;
}
