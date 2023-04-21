package com.slm.springlibrarymanagement.handler;

import com.slm.springlibrarymanagement.exceptions.InvalidDateException;
import com.slm.springlibrarymanagement.exceptions.InvalidIdException;
import com.slm.springlibrarymanagement.exceptions.NoEntriesFoundException;
import com.slm.springlibrarymanagement.exceptions.author.AuthorAlreadyExistsException;
import com.slm.springlibrarymanagement.exceptions.author.AuthorNotFoundException;
import com.slm.springlibrarymanagement.exceptions.author.InvalidAuthorNameException;
import com.slm.springlibrarymanagement.exceptions.book.BookNotFoundException;
import com.slm.springlibrarymanagement.exceptions.book.InsufficientBookQuantityException;
import com.slm.springlibrarymanagement.exceptions.book.InvalidBookNameException;
import com.slm.springlibrarymanagement.exceptions.book.InvalidNumberOfCopies;
import com.slm.springlibrarymanagement.exceptions.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice

public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleBookNotFoundException(BookNotFoundException exception) {
        log.error("Caught exception: ", exception);

        String error = exception.getMessage();
        Map<String, List<String>> errorsMap = formatErrorsResponse(error);
        return new ResponseEntity<>(errorsMap, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleClientNotFoundException(ClientNotFoundException exception) {
        log.error("Caught exception: ", exception);

        String error = exception.getMessage();
        Map<String, List<String>> errorsMap = formatErrorsResponse(error);
        return new ResponseEntity<>(errorsMap, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoEntriesFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleNoEntriesFoundException(NoEntriesFoundException exception) {
        log.error("Caught exception: ", exception);

        String error = exception.getMessage();
        Map<String, List<String>> errorsMap = formatErrorsResponse(error);
        return new ResponseEntity<>(errorsMap, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<Map<String, List<String>>> handleAuthorNotFoundException(AuthorNotFoundException exception) {
        log.error("Caught exception: ", exception);

        String error = exception.getMessage();
        Map<String, List<String>> errorsMap = formatErrorsResponse(error);
        return new ResponseEntity<>(errorsMap, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(InvalidIdException.class)
    public ResponseEntity<Map<String, List<String>>> handleInvalidIdException(InvalidIdException exception) {
        log.error("Caught exception: ", exception);

        String error = exception.getMessage();
        Map<String, List<String>> errorsMap = formatErrorsResponse(error);
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorAlreadyExistsException.class)
    public ResponseEntity<Map<String, List<String>>> handleAuthorAlreadyExistsException(AuthorAlreadyExistsException exception) {
        log.error("Caught exception: ", exception);

        String error = exception.getMessage();
        Map<String, List<String>> errorsMap = formatErrorsResponse(error);
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidAuthorNameException.class)
    public ResponseEntity<Map<String, List<String>>> handleInvalidAuthorNameException(InvalidAuthorNameException exception) {
        log.error("Caught exception: ", exception);

        String error = exception.getMessage();
        Map<String, List<String>> errorsMap = formatErrorsResponse(error);
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClientAlreadyExistsException.class)
    public ResponseEntity<Map<String, List<String>>> handleClientAlreadyExistsException(ClientAlreadyExistsException exception) {
        log.error("Caught exception: ", exception);

        String error = exception.getMessage();
        Map<String, List<String>> errorsMap = formatErrorsResponse(error);
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<Map<String, List<String>>> handleDateTimeParseException(DateTimeParseException exception) {
        log.error("Caught exception: ", exception);

        String error = "Invalid date format!";
        Map<String, List<String>> errorsMap = formatErrorsResponse(error);
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, List<String>>> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.error("Caught exception: ", exception);

        String error = exception.getMessage();
        Map<String, List<String>> errorsMap = formatErrorsResponse(error);
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidBookNameException.class)
    public ResponseEntity<Map<String, List<String>>> handleInvalidBookNameException(InvalidBookNameException exception) {
        log.error("Caught exception: ", exception);

        String error = exception.getMessage();
        Map<String, List<String>> errorsMap = formatErrorsResponse(error);
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<Map<String, List<String>>> handleInvalidDateException(InvalidDateException exception) {
        log.error("Caught exception: ", exception);

        String error = exception.getMessage();
        Map<String, List<String>> errorsMap = formatErrorsResponse(error);
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidClientFirstNameException.class)
    public ResponseEntity<Map<String, List<String>>> handleInvalidClientFirstNameException(InvalidClientFirstNameException exception) {
        log.error("Caught exception: ", exception);

        String error = exception.getMessage();
        Map<String, List<String>> errorsMap = formatErrorsResponse(error);
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidClientLastNameException.class)
    public ResponseEntity<Map<String, List<String>>> handleInvalidClientLastNameException(InvalidClientLastNameException exception) {
        log.error("Caught exception: ", exception);

        String error = exception.getMessage();
        Map<String, List<String>> errorsMap = formatErrorsResponse(error);
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidClientPhoneException.class)
    public ResponseEntity<Map<String, List<String>>> handleInvalidClientPhoneException(InvalidClientPhoneException exception) {
        log.error("Caught exception: ", exception);

        String error = exception.getMessage();
        Map<String, List<String>> errorsMap = formatErrorsResponse(error);
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidNumberOfCopies.class)
    public ResponseEntity<Map<String, List<String>>> handleInvalidNumberOfCopies(InvalidNumberOfCopies exception) {
        log.error("Caught exception: ", exception);

        String error = exception.getMessage();
        Map<String, List<String>> errorsMap = formatErrorsResponse(error);
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientBookQuantityException.class)
    public ResponseEntity<Map<String, List<String>>> handleInsufficientBookQuantityException(InsufficientBookQuantityException exception) {
        log.error("Caught exception: ", exception);

        String error = exception.getMessage();
        Map<String, List<String>> errorsMap = formatErrorsResponse(error);
        return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
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
