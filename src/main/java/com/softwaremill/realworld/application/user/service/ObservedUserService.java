package com.softwaremill.realworld.application.user.service;

import com.softwaremill.realworld.application.user.controller.LoginUserRequest;
import com.softwaremill.realworld.application.user.controller.SignUpUserRequest;
import com.softwaremill.realworld.application.user.controller.UpdateUserRequest;
import com.softwaremill.realworld.domain.user.User;
import com.softwaremill.realworld.domain.user.UserVO;

import io.micrometer.common.KeyValues;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;

public class ObservedUserService implements UserService {
    private final UserService userService;
    private final ObservationRegistry observationRegistry;

    private static final String USER_CONTEXT = "user";

    public ObservedUserService(UserService userService, ObservationRegistry observationRegistry) {
        this.userService = userService;
        this.observationRegistry = observationRegistry;
    }

    @Override
    public User signUp(SignUpUserRequest request) {
        Observation observation =
                Observation.start(USER_CONTEXT + ".signUp", this::createUserContext, observationRegistry);
        try (Observation.Scope ignored = observation.openScope()) {
            User registeredUser = userService.signUp(request);
            observation.event(Observation.Event.of("signedUp", "User signed up"));
            return registeredUser;
        } catch (Exception ex) {
            observation.error(ex);
            throw ex;
        } finally {
            observation.stop();
        }
    }

    @Override
    public UserVO login(LoginUserRequest request) {
        Observation observation =
                Observation.start(USER_CONTEXT + ".login", this::createUserContext, observationRegistry);
        try (Observation.Scope ignored = observation.openScope()) {
            UserVO loggedInUser = userService.login(request);
            observation.event(Observation.Event.of("loggedIn", "User logged in"));
            return loggedInUser;
        } catch (Exception ex) {
            observation.error(ex);
            throw ex;
        } finally {
            observation.stop();
        }
    }

    @Override
    public UserVO update(User user, UpdateUserRequest request) {
        Observation observation =
                Observation.start(USER_CONTEXT + ".update", this::createUserContext, observationRegistry);
        try (Observation.Scope ignored = observation.openScope()) {
            UserVO updatedUser = userService.update(user, request);
            observation.event(Observation.Event.of("updated", "User updated"));
            return updatedUser;
        } catch (Exception ex) {
            observation.error(ex);
            throw ex;
        } finally {
            observation.stop();
        }
    }

    private Observation.Context createUserContext() {
        Observation.Context context = new Observation.Context();
        context.addLowCardinalityKeyValues(KeyValues.of("context", "user"));
        return context;
    }
}
