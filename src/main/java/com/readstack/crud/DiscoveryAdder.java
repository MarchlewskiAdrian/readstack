package com.readstack.crud;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DiscoveryAdder {
    private final DiscoveryRepository discoveryRepository;
    private final CategoryFetcher categoryFetcher;

    public DiscoveryGetDto add(DiscoveryAddDto dto) {
        if (discoveryRepository.existsByTitleIgnoreCase(dto.title())) {
            throw new DiscoveryWithTitleAlreadyExistsException(dto.title());
        }


        Category category = categoryFetcher.getEntityById(dto.categoryId());
        Discovery discoveryToAdd = DiscoveryMapper.mapAddDtoToEntity(dto, category);

        Discovery savedDiscovery = discoveryRepository.save(discoveryToAdd);
        CategoryNameDto categoryName = CategoryMapper.mapEntityToNameDto(savedDiscovery.getCategory());
        return DiscoveryMapper.mapEntityToGetDto(savedDiscovery, categoryName);
    }
}
