package com.readstack.crud;

import com.readstack.validation.exception.CategoryContainsDiscoveriesException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CategoryDeleter {

    private final CategoryRepository categoryRepository;
    private final DiscoveryFetcher discoveryFetcher;

    public void deleteById(Long id) {
        if (containsDiscoveries(id)) {
            throw new CategoryContainsDiscoveriesException(id);
        }
        categoryRepository.deleteById(id);
    }

    private boolean containsDiscoveries(Long id) {
        return discoveryFetcher.existsByCategoryId(id);

    }
}
