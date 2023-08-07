package com.alkemy.ong.service.impl;

import com.alkemy.ong.security.dto.RoleGetDto;
import com.alkemy.ong.security.dto.RolePostDto;
import com.alkemy.ong.security.mapper.RoleMapper;
import com.alkemy.ong.security.model.Role;
import com.alkemy.ong.security.repository.RoleRepository;
import com.alkemy.ong.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public RoleGetDto save(RolePostDto dto) {
        Role savedRole = roleRepository.save(roleMapper.rolePostDtoToRole(dto));
        RoleGetDto roleDto = roleMapper.roleToRoleGetDto(savedRole);
        return roleDto;
    }

    @Override
    public List<RoleGetDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        List<RoleGetDto> rolesDtos = roleMapper.listRolesToListDtos(roles);
        return rolesDtos;
    }
}
