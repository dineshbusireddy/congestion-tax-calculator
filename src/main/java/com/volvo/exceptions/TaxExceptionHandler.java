package com.volvo.exceptions;

import com.volvo.dto.TaxCalculatorResponse;
import com.volvo.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * To provide exception handling for invalid data from client
 * <br>
 * <br>
 *
 * @author Dinesh Kumar Busireddy
 * @since 03-02-2022
 */
@Slf4j
@ControllerAdvice
public class TaxExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String CLASS_NAME = TaxExceptionHandler.class.getSimpleName();

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        String method = CLASS_NAME + ".handleMethodArgumentNotValid :";
        TaxCalculatorResponse response = new TaxCalculatorResponse();
        exception.getBindingResult().getAllErrors().forEach(
                (error) -> response.addError(error.getDefaultMessage()));

        log.error(method + exception.getMessage(), exception);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<?> handleInvalidInputException(InvalidInputException exception) {
        String method = CLASS_NAME + ".handleInvalidInputException :";
        TaxCalculatorResponse response = new TaxCalculatorResponse();
        response.addError(exception.getMessage());

        log.error(method + exception.getMessage(), exception);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        String method = CLASS_NAME + ".handleHttpMessageNotReadable :";
        TaxCalculatorResponse response = new TaxCalculatorResponse();
        String errorMessage = "Invalid request";
        if (ObjectUtil.isNotNull(exception.getCause()) && exception.getCause().getCause() instanceof InvalidInputException) {
            errorMessage = exception.getCause().getCause().getMessage();
        }
        response.addError(errorMessage);

        log.error(method + exception.getMessage(), exception);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}