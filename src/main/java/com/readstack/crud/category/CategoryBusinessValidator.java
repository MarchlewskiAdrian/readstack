package com.readstack.crud.category;

import com.readstack.crud.discovery.DiscoveryLookup;
import com.readstack.validation.exception.CategoryContainsDiscoveriesException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CategoryBusinessValidator {
    private final DiscoveryLookup discoveryLookup;

    void requireNoExistingDiscoveries(Long categoryId){
        if (discoveryLookup.existsByCategoryId(categoryId)) {
            throw new CategoryContainsDiscoveriesException(categoryId);
        }
    }

}
