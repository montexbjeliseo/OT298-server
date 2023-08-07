package com.alkemy.ong.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Getter
@Setter
@AllArgsConstructor
public class AuthRequest {



    @NotEmpty(message ="email cannot be empty" )
    @Email(message="email is not valid")
    private String username;

    @NotEmpty(message="password cannot be empty")
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$" ,
            message = "a valid password must contain a lowercase , uppercase, special caracter")
    @Size(min = 8 ,message = "password length must be 8 characters or more")
    private String password;
}
