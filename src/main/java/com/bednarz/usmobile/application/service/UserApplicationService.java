package com.bednarz.usmobile.application.service;

import com.bednarz.usmobile.application.dto.CreateUserRequest;
import com.bednarz.usmobile.application.dto.UpdateUserRequest;
import com.bednarz.usmobile.application.dto.UserResponse;
import com.bednarz.usmobile.domain.user.UserDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserApplicationService {
    private final UserDomainService userDomainService;

    public UserResponse createUser(CreateUserRequest createUserRequest) {
        log.info("Creating new user with email: {}", createUserRequest.getEmail());
        UserResponse userResponse = userDomainService.createUser(createUserRequest);
        log.debug("Created new user with id: {}", userResponse.getId());
        return userResponse;

    }

    public Optional<UserResponse> getUserById(String id) {
        log.info("Getting user by id: {}", id);
        Optional<UserResponse> response = userDomainService.getUserById(id);
        log.debug("User found: {}", response.isPresent());
        return response;
    }

    public UserResponse updateUser(String id, UpdateUserRequest updateUserRequest) {
        log.info("Updating user with id: {}", id);
        UserResponse response = userDomainService.updateUser(id, updateUserRequest);
        log.debug("Updated user: {}", response);
        return response;
    }
}
