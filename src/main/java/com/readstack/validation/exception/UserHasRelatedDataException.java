package com.readstack.validation.exception;

import com.readstack.validation.ErrorCode;

public class UserHasRelatedDataException extends ApiException {

    public UserHasRelatedDataException(Long userId, String resources) {
        super(ErrorCode.USER_IS_REFERENCED,
                "User with id: " + userId +
                " has related " + resources);
    }
}
