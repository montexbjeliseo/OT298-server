package com.alkemy.ong.security.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class RolePostDto {

    @Column(nullable = false)
    @NotNull(message="name.notNull")
    private String name;

    private String description;

    public RolePostDto(){

    }

    public RolePostDto(String name){
        this.name = name;
    }
}
