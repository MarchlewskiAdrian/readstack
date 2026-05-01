package com.readstack.crud;

class CategoryWithTitleAlreadyExistsException extends RuntimeException {
    public CategoryWithTitleAlreadyExistsException(String name) {
        super("Category with name '" + name + "' already exists");

    }
}
