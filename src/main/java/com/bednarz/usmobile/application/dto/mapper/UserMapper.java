package com.bednarz.usmobile.application.dto.mapper;

import com.bednarz.usmobile.application.dto.CreateUserRequest;
import com.bednarz.usmobile.application.dto.UpdateUserRequest;
import com.bednarz.usmobile.application.dto.UserResponse;
import com.bednarz.usmobile.domain.user.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserResponse userToUserResponse(User user);

    User createUserRequestToUser(CreateUserRequest createUserRequest);

    User createUserRequestToUser(UpdateUserRequest updateUserRequest);
}
