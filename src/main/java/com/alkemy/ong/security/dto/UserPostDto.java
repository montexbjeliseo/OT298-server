package com.alkemy.ong.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
public class UserPostDto {

    @NotBlank (message = "First name is required")
    @Pattern(regexp = "^[a-zA-Z][ a-zA-Z]*$", message = "{user.name.pattern}")
    @NotNull
    private String firstName;

    @NotBlank (message = "Last name is required")
    @Pattern(regexp = "^[a-zA-Z][ a-zA-Z]*$", message = "{user.lastname.pattern}")
    @NotNull
    private String lastName;

    @Email (message = "{email.format}")
    @NotNull (message = "{email.isRequired}")
    private String email;

    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,20}$", message = "{password.pattern}")
    @NotNull
    private String password;

    private String photo;
}
