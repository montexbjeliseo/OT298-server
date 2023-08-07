package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationUpdateDTO {
    private String name;
    private Integer phone;
    private String address;
    private String image;
    private String welcomeText;
    private String aboutUsText;

    @Pattern(regexp = "^(?:https:\\/\\/)?(?:www\\.)?(facebook|fb)\\.(com|me)\\/(?:(?:\\w\\.)*#!\\/)?(?:pages\\/)?(?:[\\w\\-\\.]*[(a-zA-ZÀ-ÿ]*)$")
    private String urlFacebook;

    @Pattern(regexp = "^(?:https:\\/\\/)?(?:www\\.)?(linkedin)\\.(com)\\/(pub|in|profile|company)\\/(?:[\\w\\-\\.]*[(a-zA-ZÀ-ÿ]*)$")
    private String urlLinkedin;

    @Pattern(regexp = "^(?:https:\\/\\/)?(?:www\\.)?(instagram)\\.(com)\\/(?:[\\w\\-\\.]*[(a-zA-ZÀ-ÿ]*)$")
    private String urlInstagram;

    @Email
    private String email;
}
