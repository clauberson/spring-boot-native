package com.softwaremill.realworld.application.user;

import com.softwaremill.realworld.IntegrationTest;
import com.softwaremill.realworld.application.user.controller.LoginUserRequest;
import com.softwaremill.realworld.application.user.controller.SignUpUserRequest;
import com.softwaremill.realworld.application.user.controller.UpdateUserRequest;
import com.softwaremill.realworld.application.user.service.UserApplicationService;
import com.softwaremill.realworld.domain.user.User;
import com.softwaremill.realworld.domain.user.UserVO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
@DisplayName("The User Services")
class UserApplicationServiceTest {
    @Autowired
    private UserApplicationService sut;

    @Test
    @DisplayName("provides membership registration function.")
    void signUp() throws Exception {
        // given
        // - sign up request
        SignUpUserRequest signUpRequest = new SignUpUserRequest("james@example.com", "james", "password");

        // when
        User user = sut.signUp(signUpRequest);

        // then
        assertThat(user.getId()).isNotNull();
        assertThat(user.getEmail()).isEqualTo("james@example.com");
        assertThat(user.getUsername()).isEqualTo("james");
        assertThat(user.getPassword()).isNotEqualTo("password");
    }

    @Test
    @DisplayName("provides login function.")
    void login() throws Exception {
        // given
        // - sign up
        SignUpUserRequest signUpRequest = new SignUpUserRequest("james@example.com", "james", "password");
        sut.signUp(signUpRequest);

        // when
        UserVO user = sut.login(new LoginUserRequest("james@example.com", "password"));

        // then
        assertThat(user.email()).isEqualTo("james@example.com");
        assertThat(user.username()).isEqualTo("james");
        assertThat(user.token()).isNotEmpty();
        assertThat(user.bio()).isEmpty();
        assertThat(user.image()).isNull();
    }

    @Test
    @DisplayName("provides member information update function.")
    void update() throws Exception {
        // given
        // - sign up
        SignUpUserRequest signUpRequest = new SignUpUserRequest("james@example.com", "james", "password");
        User user = sut.signUp(signUpRequest);

        // - update request
        String email = "james.to@example.com";
        String username = "james.to";
        String password = "5678";
        String bio = "I like to skateboard";
        String image = "https://i.stack.imgur.com/xHWG8.jpg";
        UpdateUserRequest updateRequest = new UpdateUserRequest(email, username, password, bio, image);

        // when
        UserVO userVO = sut.update(user, updateRequest);

        // then
        assertThat(userVO.email()).isEqualTo("james.to@example.com");
        assertThat(userVO.username()).isEqualTo("james.to");
        assertThat(userVO.bio()).isEqualTo("I like to skateboard");
        assertThat(userVO.image()).isEqualTo("https://i.stack.imgur.com/xHWG8.jpg");
    }
}
