package com.alkemy.ong.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ThereAreNoTestimonials extends RuntimeException{

    public ThereAreNoTestimonials(String message){
        super(message);
    }
}
