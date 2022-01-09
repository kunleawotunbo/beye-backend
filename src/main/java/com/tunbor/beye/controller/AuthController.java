package com.tunbor.beye.controller;

import com.tunbor.beye.payload.LoginRequest;
import com.tunbor.beye.payload.UserTokenResponse;
import com.tunbor.beye.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Olakunle Awotunbo
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/authenticate")
    public UserTokenResponse authenticateUser(@RequestBody @Valid LoginRequest loginRequest) {
        return userService.authenticate(loginRequest);
    }
}
