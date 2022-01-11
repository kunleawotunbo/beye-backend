package com.tunbor.beye.controller;

import com.tunbor.beye.dto.AppResponse;
import com.tunbor.beye.entity.User;
import com.tunbor.beye.service.UserService;
import com.tunbor.beye.utility.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Olakunle Awotunbo
 * @since 07/01/2022
 */
@RestController
@RequestMapping(TestController.PATH)
@RequiredArgsConstructor
public class TestController {

    public static final String PATH = AppConstants.VERSION_URL + "/test";

    private final UserService userService;

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
}
