package com.Informatorio2022.Proyecto2.exception;
import javax.validation.ConstraintViolationException;

import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptHandler {
    @Autowired
    private MessageResum messageResum;
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<MessageInfo> badException(HttpServletRequest request, BadRequestException exception) {
        return new ResponseEntity<>(new MessageInfo(exception.getMessage(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<MessageInfo> notFoundException(HttpServletRequest request, NotFoundException exception) {
        return new ResponseEntity<>(new MessageInfo(exception.getMessage(), HttpStatus.NOT_FOUND.value(), request.getRequestURI()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({NumberFormatException.class})
    public ResponseEntity<MessageInfo> numberFormatException(HttpServletRequest request) {
        return new ResponseEntity<>(new MessageInfo(messageResum.message("message.error.id.not.number", null), HttpStatus.BAD_REQUEST.value(), request.getRequestURI()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<MessagesInfo> handleValidationError(HttpServletRequest request, ConstraintViolationException exception) {
        Map<String, String> transformedError = new HashMap<>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();
            transformedError.put(fieldName.substring(fieldName.lastIndexOf('.') + 1), violation.getMessage());
        }
        return new ResponseEntity<>(new MessagesInfo(transformedError, HttpStatus.BAD_REQUEST.value(), request.getRequestURI()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<MessageInfo> httpRequestMethodNotSupportedException(HttpServletRequest request) {
        return new ResponseEntity<>(new MessageInfo(messageResum.message("user.method.not.access", null), HttpStatus.FORBIDDEN.value(), request.getRequestURI()), HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<MessageInfo> httpMessageNotReadableException(HttpServletRequest request) {
        return new ResponseEntity<>(new MessageInfo(messageResum.message("param.content.error", null), HttpStatus.BAD_REQUEST.value(), request.getRequestURI()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({TokenExpiredException.class})
    public ResponseEntity<MessageInfo> tokenExpiredException(HttpServletRequest request, TokenExpiredException ex){
        return new ResponseEntity<>(new MessageInfo(ex.getMessage(), HttpStatus.FORBIDDEN.value(), request.getRequestURI()), HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    public ResponseEntity<MessageInfo> sQLIntegrityConstraintViolationException(HttpServletRequest request, SQLIntegrityConstraintViolationException ex){
        return new ResponseEntity<>(new MessageInfo(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI()), HttpStatus.FORBIDDEN);
    }
}
