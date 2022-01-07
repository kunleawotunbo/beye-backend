package com.tunbor.beye.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Olakunle Awotunbo
 * @since 07/01/2022
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/testMe")
    public String test() {
        return "This is a test";
    }
}
