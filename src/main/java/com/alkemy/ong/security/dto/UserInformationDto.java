package com.alkemy.ong.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInformationDto {
    private String email;
    private String firstName;
    private String lastName;
    private String photo;
}
