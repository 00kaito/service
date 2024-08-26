package com.bednarz.usmobile.application.service;

import com.bednarz.usmobile.domain.dto.CreateUserRequest;
import com.bednarz.usmobile.domain.dto.UpdateUserRequest;
import com.bednarz.usmobile.domain.dto.UserResponse;
import com.bednarz.usmobile.domain.user.UserDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserApplicationService {
    private final UserDomainService userDomainService;

    public UserResponse createUser(CreateUserRequest createUserRequest) {
        return userDomainService.createUser(createUserRequest);
    }

    public Optional<UserResponse> getUserById(String id) {
        return userDomainService.getUserById(id);
    }

    public UserResponse updateUser(String id, UpdateUserRequest updateUserRequest) {
        return userDomainService.updateUser(id, updateUserRequest);
    }
}
