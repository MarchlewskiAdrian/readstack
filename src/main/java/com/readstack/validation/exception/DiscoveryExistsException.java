package com.readstack.validation.exception;

import com.readstack.validation.ErrorCode;

public class DiscoveryExistsException extends ApiException {
    public DiscoveryExistsException(String title) {
        super(
                ErrorCode.DISCOVERY_EXISTS,
                "Discovery with title '" + title + "' already exists"
        );
    }
}
