package com.tunbor.beye.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Getter
public class AppAuthority implements GrantedAuthority {

    private String authority;
}
