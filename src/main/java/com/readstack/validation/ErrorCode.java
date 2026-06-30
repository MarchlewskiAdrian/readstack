package com.readstack.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    RESOURCE_NOT_FOUND(40401, HttpStatus.NOT_FOUND),
    RESOURCE_EXISTS(40901, HttpStatus.CONFLICT),
    CATEGORY_NOT_EMPTY(40903, HttpStatus.CONFLICT),
    USER_IS_REFERENCED(40904, HttpStatus.CONFLICT),
    SELF_VOTE_ERROR(40905, HttpStatus.CONFLICT),
    DUPLICATE_VOTE_ERROR(40906, HttpStatus.CONFLICT),
    RESOURCE_MODIFIED(40907, HttpStatus.CONFLICT),
    VALIDATION_ERROR(40001, HttpStatus.BAD_REQUEST),
    FORBIDDEN(40301, HttpStatus.FORBIDDEN),
    SERVER_ERROR(50001, HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final HttpStatus status;
}

