package com.readstack.crud;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class CategoryFetcher {
    private final CategoryRepository categoryRepository;

    public PageResponse<CategoryGetDto> getAll(Pageable pageable) {

        Page<CategoryGetDto> page = categoryRepository.findAll(pageable)
                .map(CategoryMapper::mapEntityToGetDto);

        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );

    }

    public CategoryGetDto getById(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryMapper::mapEntityToGetDto)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }
    public Category getEntityById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

}
