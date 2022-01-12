package com.tunbor.beye.controller;

import com.tunbor.beye.dto.AppResponse;
import com.tunbor.beye.entity.User;
import com.tunbor.beye.service.UserService;
import com.tunbor.beye.utility.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Locale;

/**
 * @author Olakunle Awotunbo
 * @since 07/01/2022
 */

@Slf4j
@RestController
@RequestMapping(TestController.PATH)
@RequiredArgsConstructor
public class TestController {

    public static final String PATH = AppConstants.VERSION_URL + "/test";

    @Value("${placeholder.greetings}")
    private String greetings;

    private final UserService userService;

    @Autowired
    private ResourceBundleMessageSource source;

    @GetMapping("/testMe")
    public AppResponse<String> test() {
        return AppResponse.createSuccess("This is a test");
    }

    @PostMapping(value = "/register")
    public User registerUser(@RequestBody @Valid String firstName) {
        User user = new User();
        user.setFirstName(firstName);
        return userService.createTestUser(user);
    }

    @GetMapping("/message")
    public AppResponse<String> getLocaleMessage(
            @RequestHeader(name = "Accept-Language", required = false) final Locale locale,
            @RequestParam(name = "username", defaultValue = "Ola", required = false) final String username) {
        log.info("Returning greetings for locale = {}", locale);
        return AppResponse.createSuccess(source.getMessage(greetings, new Object[]{username}, locale));
    }
}
