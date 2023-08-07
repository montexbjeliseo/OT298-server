package com.alkemy.ong.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {AlreadyExistsException.class})
    protected ResponseEntity<Object> handleAlreadyExistException(AlreadyExistsException ex,
                                                                 WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<ErrorMessage> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                                           WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {PermissionDeniedException.class})
    protected ResponseEntity<ErrorMessage> handleUnauthorizedException (PermissionDeniedException ex,
                                                                        WebRequest request){
        ErrorMessage message = new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ErrorMessage> handleGlobalException(Exception ex, WebRequest request) {

        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {EntityNotSavedException.class})
    protected ResponseEntity<ErrorMessage> handleGlobalException(EntityNotSavedException ense, WebRequest request) {

        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ense.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    protected ResponseEntity<ErrorMessage> handleUsernameNotFoundException(UsernameNotFoundException ex,
                                                                           WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ThereAreNoCategories.class})
    protected ResponseEntity<Object> handleThereAreNoCategories(ThereAreNoCategories ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
      
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ThereAreNoCommentsByNew.class})
    protected ResponseEntity<Object> handleThereAreCommentsByNew(ThereAreNoCommentsByNew ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NO_CONTENT.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(value = {ThereAreNoTestimonials.class})
    protected ResponseEntity<Object> handleThereAreNoTestimonials(ThereAreNoTestimonials ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NO_CONTENT.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(value = {FileConversionException.class})
    protected ResponseEntity<ErrorMessage> handleFileConversionException(FileConversionException ex,
                                                                         WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {IOException.class})
    protected ResponseEntity<ErrorMessage> handleIOExceptionException(IOException ex,
                                                                      WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {InvalidTokenException.class})
    protected ResponseEntity<ErrorMessage> handleInvalidTokenException(InvalidTokenException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {NoAuthorizationProvidedException.class})
    protected ResponseEntity<ErrorMessage> handleNoAuthorizationProvidedException(NoAuthorizationProvidedException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.UNAUTHORIZED.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    protected ResponseEntity<ErrorMessage> handleEntityNotFoundException(EntityNotFoundException ex,
                                                                         WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {InvalidPaginationParamsException.class})
    protected ResponseEntity<ErrorMessage> handleInvalidPaginationParamsException(InvalidPaginationParamsException ex,
                                                                                  WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = PageNotFound.class)
    protected ResponseEntity<ErrorMessage> handlePageNotFoundException(PageNotFound ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = InvalidPageNumber.class)
    protected ResponseEntity<ErrorMessage> handleInvalidPageNumberException(InvalidPageNumber ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MismatchException.class})
    protected ResponseEntity<ErrorMessage> handleMismatchException(MismatchException ex,
                                                                   WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));
                
               return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(value = { NotOriginalUserException.class})
    protected ResponseEntity<ErrorMessage> handleNotOriginalUserException(NotOriginalUserException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.UNAUTHORIZED.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {ParameterNotFound.class})
    protected ResponseEntity<ErrorMessage> handleParamNotFound(ParameterNotFound ex,
                                                               WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {EntityNullException.class})
    protected ResponseEntity<ErrorMessage> handleEntityNullException(EntityNullException ex,
                                                               WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }


}