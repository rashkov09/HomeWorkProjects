package com.slm.springlibrarymanagement.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice

public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, List<String>>> handleUnexpectedException(Exception exception) {
        log.error("Caught exception: ", exception);

        String error = exception.getMessage().split(":\\s")[1];
        Map<String, List<String>> errorsMap = formatErrorsResponse(error);
        return new ResponseEntity<>(errorsMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Map<String, List<String>> formatErrorsResponse(String... errors) {
        return formatErrorsResponse(Arrays.stream(errors).collect(Collectors.toList()));
    }
    private Map<String, List<String>> formatErrorsResponse(List<String> errors) {
        Map<String, List<String>> errorResponse = new HashMap<>(4);
        errorResponse.put("Errors", errors);
        return errorResponse;
    }
}
