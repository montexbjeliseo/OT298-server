package com.alkemy.ong.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException (String message){
        super(message);
    }
}
