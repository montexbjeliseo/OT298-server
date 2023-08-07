package com.alkemy.ong.security.mapper;

import com.alkemy.ong.security.dto.RoleGetDto;
import com.alkemy.ong.security.dto.RolePostDto;
import com.alkemy.ong.security.dto.UserGetDto;
import com.alkemy.ong.security.model.Role;
import com.alkemy.ong.security.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "lastUpdate", ignore = true)
    @Mapping(target = "users", ignore = true)
    Role rolePostDtoToRole (RolePostDto dto);

    RoleGetDto roleToRoleGetDto (Role role);

    List<RoleGetDto> listRolesToListDtos (List<Role> roles);
}
