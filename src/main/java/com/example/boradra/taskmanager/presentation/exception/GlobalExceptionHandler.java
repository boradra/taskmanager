package com.example.boradra.taskmanager.presentation.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;    
import com.example.boradra.taskmanager.domain.exception.DomainTaskNotFoundException;
import com.example.boradra.taskmanager.presentation.ErrorResponse;
import java.time.LocalDate;


@ControllerAdvice
public class GlobalExceptionHandler {


    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception ex, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setStatusCode(status.value());
        errorResponse.setTimestamp(LocalDate.now());

        return new ResponseEntity<>(errorResponse, status);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DomainTaskNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTaskNotFoundException(DomainTaskNotFoundException ex) {
    
        return buildErrorResponse(ex, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowedException(MethodArgumentNotValidException ex) {

        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST);
    }
    
}
