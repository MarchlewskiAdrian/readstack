package com.readstack.validation;

import com.readstack.validation.exception.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(
                        ErrorCode.SERVER_ERROR.getCode(),
                        "Unexpected error",
                        null,
                        Instant.now()
                ));
    }


    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException e) {
        ErrorCode code = e.getErrorCode();

        return ResponseEntity
                .status(code.getStatus())
                .body(new ErrorResponse(
                        code.getCode(),
                        e.getMessage(),
                        null,
                        Instant.now()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException e) {

        List<ValidationError> errors = e
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::mapToValidationError)
                .toList();


        return ResponseEntity
                .status(ErrorCode.VALIDATION_ERROR.getStatus())
                .body(new ErrorResponse(
                        ErrorCode.VALIDATION_ERROR.getCode(),
                        "Invalid request body",
                        errors,
                        Instant.now()
                ));
    }

    private ValidationError mapToValidationError(FieldError error) {
        return  new ValidationError(
                error.getField(),
                error.getDefaultMessage()
        );
    }

}
