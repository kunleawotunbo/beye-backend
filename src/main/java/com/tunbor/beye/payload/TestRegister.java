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
public class TestRegister {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

}
