package com.readstack.crud.category;

import com.readstack.crud.PageResponse;
import com.readstack.crud.discovery.DiscoveryFacade;
import com.readstack.dto.CategoryAddDto;
import com.readstack.dto.CategoryGetDto;
import com.readstack.dto.DiscoveryGetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryFacade {

    private final CategoryFetcher categoryFetcher;
    private final CategoryUpdater categoryUpdater;
    private final CategoryDeleter categoryDeleter;
    private final CategoryAdder categoryAdder;

    private final DiscoveryFacade discoveryFacade;

    public PageResponse<CategoryGetDto> getAll(Pageable pageable) {
        return categoryFetcher.getAll(pageable);
    }

    public CategoryGetDto getById(Long id) {
        return categoryFetcher.getById(id);
    }

    public PageResponse<DiscoveryGetDto> getDiscoveriesByCategoryId(Long categoryId, Pageable pageable) {
        return discoveryFacade.getAllByCategoryId(categoryId, pageable);
    }

    public CategoryGetDto updateById(Long id, CategoryAddDto body){
        return categoryUpdater.updateById(id, body);
    }

    public void deleteById(Long id){
        categoryDeleter.deleteById(id);
    }

    public CategoryGetDto add(CategoryAddDto body) {
        return categoryAdder.add(body);
    }

    public Category getEntityById(Long categoryId) {
        return categoryFetcher.getEntityById(categoryId);
    }
}
