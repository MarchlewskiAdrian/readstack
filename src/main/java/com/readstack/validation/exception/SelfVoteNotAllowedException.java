package com.readstack.validation.exception;

import com.readstack.validation.ErrorCode;

public class SelfVoteNotAllowedException extends ApiException {
    public SelfVoteNotAllowedException() {
        super(ErrorCode.SELF_VOTE_ERROR,
                "Cannot vote for own discovery");
    }
}
