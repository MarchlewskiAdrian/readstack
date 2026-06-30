package com.readstack.validation.exception;

import com.readstack.crud.ResourceType;
import com.readstack.validation.ErrorCode;

public class ResourceExistsException extends ApiException {
    public ResourceExistsException(String fieldName, String fieldValue, ResourceType type) {
        super(
                ErrorCode.RESOURCE_EXISTS,
                type.name() + " with field '" + fieldName + " : "  + fieldValue + "' already exists"
        );
    }
}
