package com.app.georoute.mappers;

import com.app.georoute.dtos.CreateUserRequest;
import com.app.georoute.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(CreateUserRequest createUserRequest);

}
