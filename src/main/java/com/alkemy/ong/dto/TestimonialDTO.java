package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestimonialDTO {
    @NotNull(message = "{request.name}")
    private String name;

    private String image;

    @NotNull(message = "{request.content}")
    private String content;
}