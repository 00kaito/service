package com.bednarz.usmobile.domain.dto.mapper;

import com.bednarz.usmobile.domain.dto.CreateUserRequest;
import com.bednarz.usmobile.domain.dto.UpdateUserRequest;
import com.bednarz.usmobile.domain.dto.UserResponse;
import com.bednarz.usmobile.domain.dto.model.UserDto;
import com.bednarz.usmobile.domain.user.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserResponse userToUserResponse(User user);

    User createUserRequestToUser(CreateUserRequest createUserRequest);

    UserDto userDtoToUser(CreateUserRequest createUserRequest);

    User createUserRequestToUser(UpdateUserRequest updateUserRequest);
}
