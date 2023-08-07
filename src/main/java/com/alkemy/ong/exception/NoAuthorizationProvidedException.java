package com.alkemy.ong.exception;

public class NoAuthorizationProvidedException extends RuntimeException{
    public NoAuthorizationProvidedException(String error) {
        super(error);
    }
}
