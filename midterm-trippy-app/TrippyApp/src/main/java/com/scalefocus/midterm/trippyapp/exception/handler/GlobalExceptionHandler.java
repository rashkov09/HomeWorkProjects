package com.scalefocus.midterm.trippyapp.exception.handler;


import com.scalefocus.midterm.trippyapp.exception.BusinessExceptions.BusinessAlreadyExistsException;
import com.scalefocus.midterm.trippyapp.exception.BusinessExceptions.BusinessNotFoundException;
import com.scalefocus.midterm.trippyapp.exception.BusinessExceptions.BusinessTypeNotFoundException;
import com.scalefocus.midterm.trippyapp.exception.MissingRequestFieldsException;
import com.scalefocus.midterm.trippyapp.exception.NoDataFoundException;
import com.scalefocus.midterm.trippyapp.exception.UserExceptions.UserAlreadyExistsException;
import com.scalefocus.midterm.trippyapp.exception.UserExceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice

public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, List<String>>> handleUserAlreadyExists(UserAlreadyExistsException exception) {
        log.error("Caught exception: ", exception);

        String error = exception.getMessage();
        Map<String, List<String>> errorsMap = formatErrorsResponse(error);
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleControllerValidationException(MethodArgumentNotValidException exception) {
        log.error("Caught exception: ", exception);

        List<String> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());
        return new ResponseEntity<>(formatErrorsResponse(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingRequestFieldsException.class)
    public ResponseEntity<Map<String, List<String>>> handleNullPointerException(MissingRequestFieldsException exception) {
        log.error("Caught exception: ", exception);

        String error = exception.getMessage();
        Map<String, List<String>> errorsMap = formatErrorsResponse(error);
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleUserNotFoundException(UserNotFoundException exception) {
        log.error("Caught exception: ", exception);

        String error = exception.getMessage();
        Map<String, List<String>> errorsMap = formatErrorsResponse(error);
        return new ResponseEntity<>(errorsMap, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleBusinessNotFoundException(BusinessNotFoundException exception) {
        log.error("Caught exception: ", exception);

        String error = exception.getMessage();
        Map<String, List<String>> errorsMap = formatErrorsResponse(error);
        return new ResponseEntity<>(errorsMap, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessAlreadyExistsException.class)
    public ResponseEntity<Map<String, List<String>>> handleBusinessAlreadyExistsException(BusinessAlreadyExistsException exception) {
        log.error("Caught exception: ", exception);

        String error = exception.getMessage();
        Map<String, List<String>> errorsMap = formatErrorsResponse(error);
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessTypeNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleBusinessTypeNotFoundException(BusinessTypeNotFoundException exception) {
        log.error("Caught exception: ", exception);

        String error = exception.getMessage();
        Map<String, List<String>> errorsMap = formatErrorsResponse(error);
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleNoDataFoundException(NoDataFoundException exception) {
        log.error("Caught exception: ", exception);

        String error = exception.getMessage();
        Map<String, List<String>> errorsMap = formatErrorsResponse(error);
        return new ResponseEntity<>(errorsMap, HttpStatus.NOT_FOUND);
    }


    private Map<String, List<String>> formatErrorsResponse(String... errors) {
        return formatErrorsResponse(Arrays.stream(errors).collect(Collectors.toList()));
    }

    private Map<String, List<String>> formatErrorsResponse(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>(20);
        errorResponse.put("Errors", errors);
        return errorResponse;
    }
}

