package com.alkemy.ong.exception;

import javax.persistence.EntityNotFoundException;

public class EntityNotSavedException extends EntityNotFoundException {
    public EntityNotSavedException(String error) {
        super(error); }
}
