package com.alkemy.ong.dto;

import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CommentPostDto {

    @NotBlank(message = "{request.body}")
    private String body;

    @Min(value = 1,message = "{id.zero}")
    @NotNull(message = "{request.newsId}")
    private Long newsId;

}
