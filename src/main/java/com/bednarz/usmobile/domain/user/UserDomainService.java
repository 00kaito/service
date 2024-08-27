package com.bednarz.usmobile.domain.user;

import com.bednarz.usmobile.application.dto.CreateUserRequest;
import com.bednarz.usmobile.application.dto.UpdateUserRequest;
import com.bednarz.usmobile.application.dto.UserResponse;
import com.bednarz.usmobile.application.dto.mapper.UserMapper;
import com.bednarz.usmobile.infrastructure.exception.ResourceNotFoundException;
import com.bednarz.usmobile.infrastructure.persistence.UserMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDomainService {

    private final UserMongoRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse createUser(CreateUserRequest createUserRequest) {
        User user = userMapper.createUserRequestToUser(createUserRequest);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        User savedUser = userRepository.save(user);
        return userMapper.userToUserResponse(savedUser);
    }

    public Optional<UserResponse> getUserById(String userId) {
        return userRepository.findById(userId)
                .map(userMapper::userToUserResponse);
    }

    public UserResponse updateUser(String userId, UpdateUserRequest updateUserRequest) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User not found with id: " + userId);
        }
        User user = optionalUser.get();

        if (updateUserRequest.getFirstName() != null) {
            user.setFirstName(updateUserRequest.getFirstName());
        }
        if (updateUserRequest.getLastName() != null) {
            user.setLastName(updateUserRequest.getLastName());
        }
        if (updateUserRequest.getEmail() != null) {
            user.setEmail(updateUserRequest.getEmail());
        }
        User updatedUser = userRepository.save(user);
        return userMapper.userToUserResponse(updatedUser);
    }

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
}
