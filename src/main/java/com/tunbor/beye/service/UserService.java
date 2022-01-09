package com.tunbor.beye.service;

import com.tunbor.beye.entity.User;
import com.tunbor.beye.payload.LoginRequest;
import com.tunbor.beye.payload.UserTokenResponse;

import java.util.List;

/**
 * @author Olakunle Awotunbo
 * @since 06/01/2022
 */
public interface UserService {

    UserTokenResponse authenticate(LoginRequest loginRequest);

    User createUser(User user);

    User updateUser(Long id, User user);

    User createTestUser(User user);

    List<User> findAll();

}
