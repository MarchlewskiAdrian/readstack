package com.readstack.crud.category;

import com.readstack.dto.CategoryAddDto;
import com.readstack.dto.CategoryGetDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CategoryAdder {
    private final CategoryRepository categoryRepository;
    private final CategoryDataValidator categoryDataValidator;

    public CategoryGetDto add(CategoryAddDto dto) {

        categoryDataValidator.requireUniqueName(dto.name());

        Category category = CategoryMapper.mapAddDtoToEntity(dto);
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.mapEntityToGetDto(savedCategory);
    }
}
