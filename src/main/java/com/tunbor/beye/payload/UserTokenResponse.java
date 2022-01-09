package com.tunbor.beye.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Olakunle Awotunbo
 */
@AllArgsConstructor
@Getter
public class UserTokenResponse {

    private final String token;

    private final String refreshToken;
}
