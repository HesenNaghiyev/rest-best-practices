package com.restfulservices.Restful.WebServices.exception;

import com.restfulservices.Restful.WebServices.user.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

//    @Override
    //Object body, HttpHeaders headers, HttpStatus status,
    //For Handling Exception related to Server
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, WebRequest request) {
       ExceptionResponse exceptionResponse=  new ExceptionResponse(new Date(),ex.getMessage(), request.getDescription(false));
       return new ResponseEntity(exceptionResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleExceptionNotFoundl(Exception ex, WebRequest request) {
        ExceptionResponse exceptionResponse=  new ExceptionResponse(new Date(),ex.getMessage(), request.getDescription(false));
        return new ResponseEntity(exceptionResponse,HttpStatus.NOT_FOUND);
    }

    @Override
    //Property constraints related error
    //For example name is less that 5 character
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ExceptionResponse exceptionResponse=  new ExceptionResponse(new Date(),"Validation Failed", ex.getBindingResult().toString());
        return new ResponseEntity<>(exceptionResponse,HttpStatus.BAD_REQUEST);
    }
}
