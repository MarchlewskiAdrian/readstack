package com.readstack.validation.exception;

import com.readstack.crud.ResourceType;
import com.readstack.validation.ErrorCode;

public class UserHasRelatedDataException extends ApiException {

    public UserHasRelatedDataException(Long userId, ResourceType resourceType) {
        super(ErrorCode.USER_IS_REFERENCED,
                "User with id: " + userId +
                " has related " + resourceType);
    }
}
