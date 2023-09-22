package com.exercice.test.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.exercice.test.dto.UserDTO;
import com.exercice.test.entities.User;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "birthdate", target = "birthdate"),
            @Mapping(source = "county", target = "county"),
            @Mapping(source = "phone", target = "phone"),
            @Mapping(source = "gender", target = "gender")
    })
    UserDTO userToUserDTO(User user);

    @Mappings({
            @Mapping(source = "name", target = "name"),
            @Mapping(source = "birthdate", target = "birthdate"),
            @Mapping(source = "county", target = "county"),
            @Mapping(source = "phone", target = "phone"),
            @Mapping(source = "gender", target = "gender")
    })
    User userDTOToUser(UserDTO userDTO);
}