package com.alkemy.ong.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationBasicDTO {

    private String name;
    private Integer phone;
    private String address;
    private String image;
    private List<SlidesDTO> slides;
    private String urlLinkedin;
    private String urlFacebook;
    private String urlInstagram;
}
