package com.readstack.crud.category;

import com.readstack.crud.ResourceType;
import com.readstack.validation.exception.ResourceExistsException;
import com.readstack.validation.exception.ResourceNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CategoryDataValidator {
    private final CategoryRepository categoryRepository;


    public void requireUniqueName(String name) {
        if (categoryRepository.existsByNameIgnoreCase(name)){
            throw new ResourceExistsException("name", name, ResourceType.CATEGORY);
        }
    }

    public void requireCategoryExists(Long categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException(categoryId, ResourceType.CATEGORY);
        }
    }
}
