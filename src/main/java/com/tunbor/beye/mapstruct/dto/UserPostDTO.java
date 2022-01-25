package com.tunbor.beye.mapstruct.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tunbor.beye.entity.Company;
import com.tunbor.beye.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Olakunle Awotunbo
 */

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserPostDTO extends AuditableEntityDTO {

    @NotBlank
    @Size(max = 100)
    private String firstName;

    @NotBlank
    @Size(max = 100)
    private String lastName;

    @NotBlank
    @Size(max = 50)
    private String username;

    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @JsonIgnore
    private String password;

    private Company company;

    private Set<UserRole> userRoles = new HashSet<>();

}
