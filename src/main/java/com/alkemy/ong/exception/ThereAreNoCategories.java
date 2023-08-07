package com.alkemy.ong.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ThereAreNoCategories extends RuntimeException {

    public ThereAreNoCategories(String message){
        super(message);
    }
}
