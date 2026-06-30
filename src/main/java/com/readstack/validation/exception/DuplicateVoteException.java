package com.readstack.validation.exception;

import com.readstack.validation.ErrorCode;

public class DuplicateVoteException extends ApiException {
    public DuplicateVoteException() {
        super(ErrorCode.DUPLICATE_VOTE_ERROR,
                "The vote has already been added");
    }
}
