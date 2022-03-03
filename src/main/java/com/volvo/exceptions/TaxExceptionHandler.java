package com.volvo.exceptions;

import com.volvo.dto.TaxCalculatorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class TaxExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        TaxCalculatorResponse response = new TaxCalculatorResponse();
        exception.getBindingResult().getAllErrors().forEach(
                (error) -> response.addError(error.getDefaultMessage()));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<?> handleInvalidInputException(InvalidInputException exception) {
        TaxCalculatorResponse response = new TaxCalculatorResponse();
        response.addError(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        TaxCalculatorResponse response = new TaxCalculatorResponse();
        String errorMessage = "Invalid request";
        if (exception.getCause() != null && exception.getCause().getCause() instanceof InvalidInputException) {
            errorMessage = exception.getCause().getCause().getMessage();
        }
        response.addError(errorMessage);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}