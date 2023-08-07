package com.alkemy.ong.exception;

public class InvalidPaginationParamsException extends RuntimeException{
    public InvalidPaginationParamsException(String error) {
        super(error);
    }
}
