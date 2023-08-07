package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewsDto {
    @NotNull(message = "{request.name}")
    private String name;

    @NotNull(message = "{request.content}")
    private String content;

    @NotNull(message = "{request.image}")
    private String image;

    @NotNull(message = "{request.categoryId}")
    private Long categoryId;
}
