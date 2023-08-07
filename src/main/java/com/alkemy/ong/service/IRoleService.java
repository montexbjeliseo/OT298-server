package com.alkemy.ong.service;

import com.alkemy.ong.security.dto.RoleGetDto;
import com.alkemy.ong.security.dto.RolePostDto;

import java.util.List;

public interface IRoleService {

    RoleGetDto save (RolePostDto dto);

    List<RoleGetDto> getAllRoles();
}
