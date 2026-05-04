package com.readstack.validation;

public record ValidationError(
        String field,
        String error
) {
}
