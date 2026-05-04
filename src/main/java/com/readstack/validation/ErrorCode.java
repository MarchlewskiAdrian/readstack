package com.readstack.validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    CATEGORY_NOT_FOUND(40401, HttpStatus.NOT_FOUND),
    DISCOVERY_NOT_FOUND(40402, HttpStatus.NOT_FOUND),
    DISCOVERY_EXISTS(40901, HttpStatus.CONFLICT),
    CATEGORY_EXISTS(40902, HttpStatus.CONFLICT),
    CATEGORY_NOT_EMPTY(40903, HttpStatus.CONFLICT),
    VALIDATION_ERROR(40001, HttpStatus.BAD_REQUEST),
    SERVER_ERROR(50001, HttpStatus.INTERNAL_SERVER_ERROR);

    private final int code;
    private final HttpStatus status;
}

