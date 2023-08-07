package com.alkemy.ong.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ThereAreNoCommentsByNew extends RuntimeException {

    public ThereAreNoCommentsByNew(String message){
        super(message);
    }

}
