package com.readstack.crud.category;

import com.readstack.dto.CategoryAddDto;
import com.readstack.dto.CategoryGetDto;
import com.readstack.dto.CategoryNameDto;

public class CategoryMapper {
    public static CategoryGetDto mapEntityToGetDto(Category category) {
        return new CategoryGetDto(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    public static CategoryNameDto mapEntityToNameDto(Category category){
        return new CategoryNameDto(
                category.getId(),
                category.getName()
        );
    }

    public static Category mapAddDtoToEntity(CategoryAddDto dto) {
        Category entity = new Category();
        entity.setName(dto.name());
        entity.setDescription(dto.description());

        return entity;
    }
}
