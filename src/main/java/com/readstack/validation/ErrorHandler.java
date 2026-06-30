package com.readstack.validation;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.readstack.validation.exception.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
                .status(ErrorCode.SERVER_ERROR.getStatus())
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
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFoundException(UsernameNotFoundException e) {

        return ResponseEntity
                .status(ErrorCode.RESOURCE_NOT_FOUND.getStatus())
                .body(new ErrorResponse(
                        ErrorCode.RESOURCE_NOT_FOUND.getCode(),
                        e.getMessage(),
                        null,
                        Instant.now()
                ));
    }


    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleTokenExpiredException(TokenExpiredException e){
        return ResponseEntity
                .status(ErrorCode.FORBIDDEN.getStatus())
                .body(new ErrorResponse(
                        ErrorCode.FORBIDDEN.getCode(),
                        e.getMessage(),
                        null,
                        Instant.now()
                ));
    }


    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<ErrorResponse> handleOptimisticLockingException(ObjectOptimisticLockingFailureException e){
        return ResponseEntity
                .status(ErrorCode.RESOURCE_MODIFIED.getStatus())
                .body(new ErrorResponse(
                        ErrorCode.RESOURCE_MODIFIED.getCode(),
                        "Resource modified by other user",
                        null,
                        Instant.now()
                ));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e){
        return ResponseEntity
                .status(ErrorCode.VALIDATION_ERROR.getStatus())
                .body(new ErrorResponse(
                        ErrorCode.VALIDATION_ERROR.getCode(),
                        "Bad Credentials",
                        null,
                        Instant.now()));
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
