package com.tunbor.beye.controller;

import com.tunbor.beye.entity.Company;
import com.tunbor.beye.entity.Tenant;
import com.tunbor.beye.entity.User;
import com.tunbor.beye.entity.UserRole;
import com.tunbor.beye.entity.enums.Role;
import com.tunbor.beye.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

/**
 * @author Olakunle
 * @since 06/01/2022
 */

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/test")
    public String test() {

        return "This is a test";
    }

    @PostMapping(value = "/register")
    public String registerUser(@RequestBody @Valid String firstName) {

        Company company = new Company();
        company.setName("Sofort");

        Tenant tenant = new Tenant();
        tenant.setName("Test Tenant");
        tenant.setCompanies(Collections.singleton(company));

        UserRole userRole = new UserRole();
        userRole.setRole(Role.ADMIN);

        User user = new User();
//        user.setFirstName("Babatunde");
        user.setFirstName(firstName);
        user.setLastName("Fashola");
        user.setUsername("test");
        user.setEmail("test@test.com");
        user.setPassword("Test");
        user.setCompany(company);
        user.setRoles(Collections.singleton(userRole));

        userService.createUser(user);

        return "OK testing";
    }
}
