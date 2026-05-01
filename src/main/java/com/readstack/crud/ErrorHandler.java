package com.readstack.crud;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
class ErrorHandler {
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(CategoryNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        40401,
                        e.getMessage(),
                        Instant.now()
                ));
    }

    @ExceptionHandler(DiscoveryNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(DiscoveryNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(
                        40402,
                        e.getMessage(),
                        Instant.now()
                ));
    }

    @ExceptionHandler(DiscoveryWithTitleAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(DiscoveryWithTitleAlreadyExistsException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        40901,
                        e.getMessage(),
                        Instant.now()
                ));
    }

    @ExceptionHandler(CategoryContainsDiscoveriesException.class)
    public ResponseEntity<ErrorResponse> handleIncompatibility(CategoryContainsDiscoveriesException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        40902,
                        e.getMessage(),
                        Instant.now()
                ));
    }
}
