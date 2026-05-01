package com.readstack.crud;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DiscoveryUpdater {

    private final DiscoveryFetcher discoveryFetcher;
    private final DiscoveryRepository discoveryRepository;
    private final CategoryFetcher categoryFetcher;

    public DiscoveryGetDto updateById(Long id, DiscoveryAddDto dto) {
        Discovery discovery = discoveryFetcher.getEntityById(id);
        Category category = categoryFetcher.getEntityById(dto.categoryId());
        CategoryNameDto categoryNameDto = CategoryMapper.mapEntityToNameDto(category);

        discovery.setTitle(dto.title());
        discovery.setUrl(dto.url());
        discovery.setDescription(dto.description());
        discovery.setCategory(category);

        Discovery savedDiscovery = discoveryRepository.save(discovery);
        return DiscoveryMapper.mapEntityToGetDto(savedDiscovery, categoryNameDto);

    }
}
