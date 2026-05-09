package com.readstack.crud;

import com.readstack.dto.CategoryNameDto;
import com.readstack.dto.DiscoveryAddDto;
import com.readstack.dto.DiscoveryGetDto;
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
    private final DiscoveryValidator discoveryValidator;

    public DiscoveryGetDto add(DiscoveryAddDto dto) {
        discoveryValidator.validateForAdd(dto);

        Category category = categoryFetcher.getEntityById(dto.categoryId());
        Discovery discoveryToAdd = DiscoveryMapper.mapAddDtoToEntity(dto, category);

        Discovery savedDiscovery = discoveryRepository.save(discoveryToAdd);
        CategoryNameDto categoryName = CategoryMapper.mapEntityToNameDto(savedDiscovery.getCategory());
        return DiscoveryMapper.mapEntityToGetDto(savedDiscovery, categoryName);
    }
}
