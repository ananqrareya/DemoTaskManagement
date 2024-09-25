package com.ListTasks.LTasks.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RegisterUserExceptions.class)
    public ResponseEntity<?> handleRegisterUserException(RegisterUserExceptions registerUserExceptions, WebRequest request){
        ErrorDetails errorDetails =new ErrorDetails(
                HttpStatus.BAD_REQUEST.toString(),
                registerUserExceptions.getMessage(),
                new Date() );
        return new ResponseEntity<>(errorDetails,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<?> handleTokenNotFoundException(TokenNotFoundException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.NOT_FOUND.toString(),
                exception.getMessage(),
                new Date()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<?> handleTokenExpiredException(TokenExpiredException exception, WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.GONE.toString(),
                exception.getMessage(),
                new Date()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.GONE);

    }
}
