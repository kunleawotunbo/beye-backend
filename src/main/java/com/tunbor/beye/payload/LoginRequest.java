package com.tunbor.beye.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @author Olakunle Awotunbo
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LoginRequest {

    @NotEmpty
    private String usernameOrEmail;

    @NotEmpty
    private String password;
}
