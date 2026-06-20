package com.readstack.crud.category;

import com.readstack.crud.discovery.DiscoveryFacade;
import com.readstack.validation.exception.CategoryContainsDiscoveriesException;
import com.readstack.validation.exception.CategoryNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CategoryDeleter {

    private final CategoryRepository categoryRepository;
    private final DiscoveryFacade discoveryFacade;

    public void deleteById(Long id) {
        if (containsDiscoveries(id)) {
            throw new CategoryContainsDiscoveriesException(id);
        }
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException(id);
        }
        categoryRepository.deleteById(id);
    }

    private boolean containsDiscoveries(Long id) {
        return discoveryFacade.existsByCategoryId(id);

    }
}
