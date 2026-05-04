package com.readstack.validation.exception;

import com.readstack.validation.ErrorCode;

public class CategoryExistsException extends ApiException {
    public CategoryExistsException(String name) {
        super(
                ErrorCode.CATEGORY_EXISTS,
                "Category with name '" + name + "' already exists"
        );

    }
}
