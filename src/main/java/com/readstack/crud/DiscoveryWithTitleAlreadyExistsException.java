package com.readstack.crud;

class DiscoveryWithTitleAlreadyExistsException extends RuntimeException {
    public DiscoveryWithTitleAlreadyExistsException(String title) {
        super("Discovery with title '" + title + "' already exists");
    }
}
