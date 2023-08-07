package com.alkemy.ong.dto;

import com.alkemy.ong.security.dto.RoleGetDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import lombok.AllArgsConstructor;

@Setter
@Getter
@AllArgsConstructor
public class AuxUserGetDto {

    private String firstName;
    private String lastName;
    private String email;
    private Set<RoleGetDto> roles;

}