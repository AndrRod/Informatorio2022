package com.Informatorio2022.Proyecto2.exception;

import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptHandler {
    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<MessageInfo> badException(HttpServletRequest request, BadRequestException exception) {
        String message = exception.getMessage();
        MessageInfo errorInfo = new MessageInfo(message, HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<MessageInfo> notFoundException(HttpServletRequest request, NotFoundException exception) {
        String message = exception.getMessage();
        MessageInfo errorInfo = new MessageInfo(message, HttpStatus.NOT_FOUND.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({NumberFormatException.class})
    public ResponseEntity<MessageInfo> numberFormatException(HttpServletRequest request) {
        String message = "message.error.id.not.number";
        MessageInfo errorInfo = new MessageInfo(message, HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<MessageInfo> noSuchElementException(HttpServletRequest request, NoSuchElementException ex) {
        String message = "entity not found";
        MessageInfo errorInfo = new MessageInfo(message, HttpStatus.NOT_FOUND.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<MessagesInfo> handleValidationError(HttpServletRequest request, ConstraintViolationException exception) {
        Map<String, String> transformedError = new HashMap<>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();
            transformedError.put(fieldName.substring(fieldName.lastIndexOf('.') + 1), violation.getMessage());
        }
        MessagesInfo errorInfo = new MessagesInfo(transformedError, HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }
}
