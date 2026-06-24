package com.readstack.crud.category;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CategoryLookupImpl implements CategoryLookup {
    private final CategoryFetcher categoryFetcher;

    @Override
    public Category getEntityById(Long categoryId) {
        return categoryFetcher.getEntityById(categoryId);

    }
}
