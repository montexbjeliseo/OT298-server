package com.alkemy.ong.exception;

public class PermissionDeniedException extends RuntimeException{
    public PermissionDeniedException(String error){
        super(error);
    }
}
