package com.alkemy.ong.security.mapper;

import com.alkemy.ong.dto.AuxUserGetDto;
import com.alkemy.ong.security.dto.UserGetDto;
import com.alkemy.ong.security.dto.UserInformationDto;
import com.alkemy.ong.security.dto.UserPostDto;
import com.alkemy.ong.security.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel="spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "lastUpdated", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User userPostDtoToUser (UserPostDto dto);

    @Mapping(target = "jwtToken", ignore = true)
    UserGetDto userToUserDto (User user);
    
    List<UserGetDto> listUsersToListDtos (List<User> users);
    
    List<AuxUserGetDto> toAuxList (List<User> users);
    AuxUserGetDto toAuxDto(User user);

    UserInformationDto userToUserInformationDto(User user);
}
