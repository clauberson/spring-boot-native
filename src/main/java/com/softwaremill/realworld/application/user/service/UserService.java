package com.softwaremill.realworld.application.user.service;

import com.softwaremill.realworld.application.user.controller.LoginUserRequest;
import com.softwaremill.realworld.application.user.controller.SignUpUserRequest;
import com.softwaremill.realworld.application.user.controller.UpdateUserRequest;
import com.softwaremill.realworld.domain.user.User;
import com.softwaremill.realworld.domain.user.UserVO;

public interface UserService {
    User signUp(SignUpUserRequest request);

    UserVO login(LoginUserRequest request);

    UserVO update(User user, UpdateUserRequest request);
}
