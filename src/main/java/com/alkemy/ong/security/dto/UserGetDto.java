package com.alkemy.ong.security.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
public class UserGetDto {

    private String firstName;
    private String email;
    private Set<RoleGetDto> roles;
    private String jwtToken;

}
