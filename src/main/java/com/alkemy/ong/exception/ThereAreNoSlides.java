package com.alkemy.ong.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ThereAreNoSlides extends RuntimeException{

    public ThereAreNoSlides(String message){
        super(message);
    }
}
