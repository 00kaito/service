package com.bednarz.usmobile.api;

import com.bednarz.usmobile.application.ApiResponse;
import com.bednarz.usmobile.domain.dto.CreateUserRequest;
import com.bednarz.usmobile.domain.dto.UpdateUserRequest;
import com.bednarz.usmobile.domain.dto.UserResponse;
import com.bednarz.usmobile.domain.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        UserResponse createdUser = userService.createUser(createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(createdUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable String id) {
        Optional<UserResponse> user = userService.getUserById(id);
        return user.map(value -> ResponseEntity.ok(ApiResponse.success(value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("User not found")));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable String id, @RequestBody @Valid UpdateUserRequest updateUserRequest) {
        UserResponse updatedUser = userService.updateUser(id, updateUserRequest);
        return ResponseEntity.ok(ApiResponse.success(updatedUser));
    }
}
