package com.softwaremill.realworld.application.user.service;

import com.softwaremill.realworld.domain.user.ProfileVO;
import com.softwaremill.realworld.domain.user.User;
import com.softwaremill.realworld.domain.user.UserRepository;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ProfileVO getProfile(User me, String targetUsername) {
        return this.getProfile(me, findUserByUsername(targetUsername));
    }

    @Transactional(readOnly = true)
    public ProfileVO getProfile(User me, User target) {
        return new ProfileVO(me, target);
    }

    @Transactional
    public ProfileVO follow(User me, String targetUsername) {
        return this.follow(me, findUserByUsername(targetUsername));
    }

    @Transactional
    public ProfileVO follow(User me, User target) {
        return me.follow(target);
    }

    @Transactional
    public ProfileVO unfollow(User me, String targetUsername) {
        return this.unfollow(me, findUserByUsername(targetUsername));
    }

    @Transactional
    public ProfileVO unfollow(User me, User target) {
        return me.unfollow(target);
    }

    private User findUserByUsername(String username) {
        String message = "User(`%s`) not found".formatted(username);
        return userRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException(message));
    }
}
