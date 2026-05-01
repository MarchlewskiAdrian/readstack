package com.readstack.crud;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.antlr.v4.runtime.misc.MurmurHash;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Facade {
    private final DiscoveryFetcher discoveryFetcher;
    private final DiscoveryAdder discoveryAdder;
    private DiscoveryUpdater discoveryUpdater;
    private DiscoveryDeleter discoveryDeleter;

    private final CategoryFetcher categoryFetcher;
    private final CategoryUpdater categoryUpdater;
    private final CategoryDeleter categoryDeleter;
    private final CategoryAdder categoryAdder;

    public PageResponse<DiscoveryGetDto> getAllDiscoveries(String title, Pageable pageable){
        return discoveryFetcher.getAll(title, pageable);
    }
    public PageResponse<DiscoveryGetDto> getAllDiscoveriesByCategoryId(Long categoryId, Pageable pageable){
        return discoveryFetcher.getAllByCategoryId(categoryId, pageable);
    }

    public DiscoveryGetDto getDiscoveryById(Long id) {
        return discoveryFetcher.getById(id);
    }

    public DiscoveryGetDto addDiscovery(DiscoveryAddDto body) {
        return discoveryAdder.add(body);
    }
    public DiscoveryGetDto updateDiscoveryById(Long discoveryId, DiscoveryAddDto body){
        return discoveryUpdater.updateById(discoveryId, body);
    }

    public void deleteDiscoveryById(Long discoveryId){
        discoveryDeleter.deleteById(discoveryId);
    }

    public PageResponse<CategoryGetDto> getAllCategories(Pageable pageable) {
        return categoryFetcher.getAll(pageable);
    }

    public CategoryGetDto getCategoryById(Long id) {
        return categoryFetcher.getById(id);
    }

    public CategoryGetDto updateCategoryById(Long id, CategoryAddDto body){
        return categoryUpdater.updateById(id, body);
    }

    public void deleteCategoryById(Long id){
        categoryDeleter.deleteById(id);
    }

    public CategoryGetDto addCategory(CategoryAddDto body) {
        return categoryAdder.add(body);

    }
}
