package com.alkemy.ong.dto;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class MemberDTO {

    private Long id;

    @NotNull(message = "{request.name}")
    @Pattern(regexp = "^[a-zA-Z][ a-zA-Z]*$", message = "{member.name.pattern.error}")
    private String name;

    private String facebookUrl;

    private String instagramUrl;

    private String linkedinUrl;

    @NotNull(message = "{request.image}")
    private String image;

    private String description;

}
