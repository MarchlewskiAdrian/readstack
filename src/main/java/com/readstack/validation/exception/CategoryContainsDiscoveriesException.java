package com.readstack.validation.exception;

import com.readstack.validation.ErrorCode;

public class CategoryContainsDiscoveriesException extends ApiException {
    public CategoryContainsDiscoveriesException(Long id) {
        super(
                ErrorCode.CATEGORY_NOT_EMPTY,
                "Category with id " + id + " contains discoveries"
        );
    }
}
