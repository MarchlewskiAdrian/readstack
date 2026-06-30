package com.readstack.crud.category;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CategoryDeleter {

    private final CategoryRepository categoryRepository;
    private final CategoryDataValidator categoryDataValidator;
    private final CategoryBusinessValidator categoryBusinessValidator;

    public void deleteById(Long categoryId) {

        categoryDataValidator.requireCategoryExists(categoryId);
        categoryBusinessValidator.requireNoExistingDiscoveries(categoryId);

        categoryRepository.deleteById(categoryId);
    }

}
