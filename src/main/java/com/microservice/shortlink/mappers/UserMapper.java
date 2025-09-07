package com.microservice.shortlink.mappers;

import com.microservice.shortlink.dtos.RegisterUserRequest;
import com.microservice.shortlink.dtos.UpdateUserRequest;
import com.microservice.shortlink.dtos.UserDto;
import com.microservice.shortlink.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);
    void update(UpdateUserRequest request, @MappingTarget User user);
}
