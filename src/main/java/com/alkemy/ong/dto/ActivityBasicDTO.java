package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityBasicDTO {

    private String name;

    @Lob
    private String content;

    private String image;

}
