package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SlidesDTO implements Serializable {


    private Long id;

    private String image;

    private String text;

    private Integer position;
}
