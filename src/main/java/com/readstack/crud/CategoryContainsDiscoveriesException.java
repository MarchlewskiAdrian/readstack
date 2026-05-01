package com.readstack.crud;

class CategoryContainsDiscoveriesException extends RuntimeException {
    public CategoryContainsDiscoveriesException(Long id) {
        super("Category with id " + id + " contains discoveries");
    }
}
