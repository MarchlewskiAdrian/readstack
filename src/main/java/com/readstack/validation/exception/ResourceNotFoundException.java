package com.readstack.validation.exception;

import com.readstack.crud.ResourceType;
import com.readstack.validation.ErrorCode;

public class ResourceNotFoundException extends ApiException {

    public ResourceNotFoundException(Long resourceId, ResourceType resourceType) {
        super(
                ErrorCode.RESOURCE_NOT_FOUND,
                "%s with id %s not found"
                        .formatted(resourceType.name().toLowerCase(), resourceId)
        );
    }
}
