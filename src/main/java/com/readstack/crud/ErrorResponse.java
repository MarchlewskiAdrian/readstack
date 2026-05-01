package com.readstack.crud;

import java.time.Instant;

public record ErrorResponse(
        int code,
        String msg,
        Instant timestamp
) {
}
