package com.readstack.crud.category;

import com.readstack.dto.CategoryAddDto;
import com.readstack.dto.CategoryGetDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CategoryUpdater {
    private final CategoryFetcher categoryFetcher;
    private final CategoryRepository categoryRepository;

    public CategoryGetDto updateById(Long id, CategoryAddDto body) {
        Category category = categoryFetcher.getEntityById(id);

        category.setName(body.name());
        category.setDescription(body.description());

        Category savedCategory = categoryRepository.save(category); //TODO: category == savedCategory, use dirty checking
        return CategoryMapper.mapEntityToGetDto(savedCategory);
    }


}
