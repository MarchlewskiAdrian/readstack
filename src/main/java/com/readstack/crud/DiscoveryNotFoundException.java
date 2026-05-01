package com.readstack.crud;

class DiscoveryNotFoundException extends RuntimeException {
    public DiscoveryNotFoundException(Long id) {
        super("Discovery with id " + id + " not found");
    }
}
