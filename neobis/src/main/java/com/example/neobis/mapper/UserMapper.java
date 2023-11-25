package com.example.neobis.mapper;

import com.example.neobis.dto.SaveUserDto;
import com.example.neobis.dto.UserDto;
import com.example.neobis.entity.User;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto entityToDto(User user);

    User dtoToEntity(UserDto userDto);

    List<UserDto> entitiesToDtos(List<User> users);

    User saveDtoToEntity(SaveUserDto userSaveDto);
}
