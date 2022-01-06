package com.tunbor.beye.service;

import com.tunbor.beye.entity.User;

import java.util.UUID;

/**
 * @author Olakunle Awotunbo
 * @since 06/01/2022
 */
public interface UserService {

    User createUser(User user);

    User updateUser(UUID id, User user);

    User updateUserById(Long id, User user);
}
