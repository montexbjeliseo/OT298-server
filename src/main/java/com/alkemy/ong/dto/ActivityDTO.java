package com.alkemy.ong.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActivityDTO {

    @NotNull(message = "{request.name}")
    private String name;

    @NotNull(message = "{request.content}")
    @Lob
    private String content;

    @NotNull(message = "{request.image}")
    private String image;
}
