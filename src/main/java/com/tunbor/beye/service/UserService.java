package com.tunbor.beye.service;

import com.tunbor.beye.entity.User;

/**
 * @author Olakunle Awotunbo
 * @since 06/01/2022
 */
public interface UserService {

    User createUser(User user);

    User updateUser(Long id, User user);

}
