package com.readstack.validation.exception;

import com.readstack.validation.ErrorCode;

public class UserNotFoundException extends ApiException {


    public UserNotFoundException(Long id) {
        super(
                ErrorCode.USER_NOT_FOUND,
                id == null ? "User not found" : "User with id " + id + " not found"
        );
    }


}
