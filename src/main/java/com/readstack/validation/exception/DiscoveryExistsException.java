package com.readstack.validation.exception;

import com.readstack.validation.ErrorCode;

public class DiscoveryExistsException extends ApiException {
    public DiscoveryExistsException(String fieldName, String field) {
        super(
                ErrorCode.DISCOVERY_EXISTS,
                "Discovery with field '" + fieldName + " : "  + field + "' already exists"
        );
    }
}
