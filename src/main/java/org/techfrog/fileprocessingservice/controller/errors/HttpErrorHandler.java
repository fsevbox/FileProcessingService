package org.techfrog.fileprocessingservice.controller.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class HttpErrorHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpErrorHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleArgumentNotValid(MethodArgumentNotValidException e) {
        LOGGER.error(e.getMessage());
        List<String> errors = e.getBindingResult().getFieldErrors().stream()
                .map(ex -> ex.getField() + ":" + ex.getDefaultMessage())
                .collect(Collectors.toList());
        return new ResponseEntity<>(
                new ApiError(HttpStatus.BAD_REQUEST.toString(), errors),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiError> noHandlerFoundException(NoHandlerFoundException e) {
        LOGGER.error(e.getMessage());
        return new ResponseEntity<>(
                new ApiError(HttpStatus.NOT_FOUND.toString(), List.of(e.getMessage())),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception e) {
        LOGGER.error(e.getMessage());
        return new ResponseEntity<>(
                new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.toString(), List.of("Unknown error occured")),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
