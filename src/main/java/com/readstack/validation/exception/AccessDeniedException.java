package com.readstack.validation.exception;

import com.readstack.crud.ResourceType;
import com.readstack.validation.ErrorCode;

public class AccessDeniedException extends ApiException {
    public AccessDeniedException(Long userId, Long discoveryCreatorId, ResourceType resourceType) {
        super(ErrorCode.FORBIDDEN,
                "Access denied for user %s to %s with id %s"
                        .formatted(userId, resourceType.name().toLowerCase(), discoveryCreatorId));
    }
}
