package com.readstack.crud;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DiscoveryDeleter {
    private final DiscoveryRepository discoveryRepository;

    public void deleteById(Long id) {
        discoveryRepository.deleteById(id);
    }
}