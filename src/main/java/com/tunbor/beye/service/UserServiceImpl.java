package com.tunbor.beye.service;

import com.tunbor.beye.entity.User;
import com.tunbor.beye.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author Olakunle Awotunbo
 * @since 06/01/2022
 */

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UUID uuid, User user) {
//        userRepository.findByUuid(uuid).ifPre
        /*
        userRepository.findById(100L).ifPresent(existingUser -> {
            if (!existingUser.getId().equals(userId))
                ServiceUtils.throwDuplicateNameException("User", request.getName());
        });
        */
        return null;
    }

    @Override
    public User updateUserById(Long userId, User user) {
        /*
        userRepository.findById(userId).ifPresent(existingUser -> {
            if (!existingUser.getId().equals(userId))
                ServiceUtils.throwDuplicateNameException("User", request.getName());
        });
        */
        return null;
    }
}
