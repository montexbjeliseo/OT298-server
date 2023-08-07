package com.alkemy.ong.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {

    private Long id;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z\\s]*$")
    private String name;

    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$")
    private String phone;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String message;
}
