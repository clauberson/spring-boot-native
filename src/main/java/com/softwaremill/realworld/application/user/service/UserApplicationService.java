package com.softwaremill.realworld.application.user.service;

import com.softwaremill.realworld.application.config.BearerTokenSupplier;
import com.softwaremill.realworld.application.user.controller.LoginUserRequest;
import com.softwaremill.realworld.application.user.controller.SignUpUserRequest;
import com.softwaremill.realworld.application.user.controller.UpdateUserRequest;
import com.softwaremill.realworld.domain.user.User;
import com.softwaremill.realworld.domain.user.UserRepository;
import com.softwaremill.realworld.domain.user.UserVO;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

public class UserApplicationService implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BearerTokenSupplier bearerTokenSupplier;

    public UserApplicationService(
            UserRepository userRepository, PasswordEncoder passwordEncoder, BearerTokenSupplier bearerTokenSupplier) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.bearerTokenSupplier = bearerTokenSupplier;
    }

    @Transactional
    @Override
    public User signUp(SignUpUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email(`%s`) already exists.".formatted(request.email()));
        }
        if (userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Username(`%s`) already exists.".formatted(request.username()));
        }

        User newUser = this.createNewUser(request);
        return userRepository.save(newUser);
    }

    @Transactional(readOnly = true)
    @Override
    public UserVO login(LoginUserRequest request) {
        return userRepository
                .findByEmail(request.email())
                .filter(user -> passwordEncoder.matches(request.password(), user.getPassword()))
                .map(user -> {
                    String token = bearerTokenSupplier.supply(user);
                    return new UserVO(user.possessToken(token));
                })
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
    }

    @Transactional
    @Override
    public UserVO update(User user, UpdateUserRequest request) {
        String email = request.email();
        if (!user.getEmail().equals(email) && userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email(`%s`) already exists.".formatted(email));
        }

        String username = request.username();
        if (!user.getUsername().equals(username) && userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username(`%s`) already exists.".formatted(request.username()));
        }

        user.updateEmail(email);
        user.updateUsername(username);
        user.updatePassword(passwordEncoder, request.password());
        user.updateBio(request.bio());
        user.updateImage(request.image());

        return new UserVO(user);
    }

    private User createNewUser(SignUpUserRequest request) {
        return User.builder()
                .email(request.email())
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .build();
    }
}
