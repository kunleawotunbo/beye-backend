package com.tunbor.beye.controller;

import com.tunbor.beye.dto.AppResponse;
import com.tunbor.beye.entity.Company;
import com.tunbor.beye.entity.Tenant;
import com.tunbor.beye.entity.User;
import com.tunbor.beye.entity.UserRole;
import com.tunbor.beye.entity.enums.Role;
import com.tunbor.beye.mapstruct.dto.UserGetDTO;
import com.tunbor.beye.service.TokenBlockService;
import com.tunbor.beye.service.UserService;
import com.tunbor.beye.utility.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author Olakunle
 * @since 06/01/2022
 */

@RestController
@RequestMapping(UserController.PATH)
@RequiredArgsConstructor
public class UserController {
    public static final String PATH = AppConstants.VERSION_URL + "/users";

    private final UserService userService;

    private final TokenBlockService tokenBlockService;

    private final ResourceBundleMessageSource source;

    @GetMapping()
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/all")
    public List<UserGetDTO> findAllDTO() {
        return userService.findAllDTO();
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
        user.setUserRoles(Collections.singleton(userRole));

        userService.createUser(user);

        return "OK testing";
    }


    @PostMapping("/revoke-token")
    public AppResponse<String> revokeToken(HttpServletRequest request, final Locale locale) {
        tokenBlockService.revokeToken(request);

        return AppResponse.createSuccess(source.getMessage("token.revoked", null, locale));
    }
}
