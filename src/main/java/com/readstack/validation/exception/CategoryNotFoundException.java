package com.readstack.validation.exception;

import com.readstack.validation.ErrorCode;

public class CategoryNotFoundException extends ApiException {
    public CategoryNotFoundException(Long id) {
        super(
                ErrorCode.CATEGORY_NOT_FOUND,
                "Category with id " + id + " not found"
        );
    }
}
