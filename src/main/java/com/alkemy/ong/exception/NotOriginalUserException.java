package com.alkemy.ong.exception;

public class NotOriginalUserException extends RuntimeException{
    public NotOriginalUserException(String error) {
        super(error);
    }
}
