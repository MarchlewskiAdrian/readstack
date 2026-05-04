package com.readstack.validation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.time.Instant;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public record ErrorResponse(
        int code,
        String msg,
        List<ValidationError> errors,
        Instant timestamp
) {
}
