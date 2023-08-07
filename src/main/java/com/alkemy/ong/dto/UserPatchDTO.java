package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class UserPatchDTO {

    @Pattern(regexp = "^[a-zA-Z][ a-zA-Z]*$", message = "{user.name.pattern}")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z][ a-zA-Z]*$", message = "{user.lastname.pattern}")
    private String lastName;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,20}$", message = "{password.pattern}")
    private String password;

    private String photo;
}
