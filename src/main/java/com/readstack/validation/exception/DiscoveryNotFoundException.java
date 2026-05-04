package com.readstack.validation.exception;

import com.readstack.validation.ErrorCode;

public class DiscoveryNotFoundException extends ApiException {

    public DiscoveryNotFoundException(Long id) {
        super(
                ErrorCode.DISCOVERY_NOT_FOUND,
                "Discovery with id " + id + " not found"
        );
    }
}
