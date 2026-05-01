package com.readstack.crud;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CategoryAdder {
    private final CategoryRepository categoryRepository;

    public CategoryGetDto add(CategoryAddDto dto) {
        if(categoryRepository.existsByNameIgnoreCase(dto.name())){
            throw new CategoryWithTitleAlreadyExistsException(dto.name());
        }

        Category category = CategoryMapper.mapAddDtoToEntity(dto);
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.mapEntityToGetDto(savedCategory);
    }
}
