package com.readstack.crud.discovery;

import com.readstack.crud.category.Category;
import com.readstack.crud.category.CategoryLookup;
import com.readstack.crud.category.CategoryMapper;
import com.readstack.dto.CategoryNameDto;
import com.readstack.dto.DiscoveryAddDto;
import com.readstack.dto.DiscoveryGetDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DiscoveryAdder {
    private final DiscoveryRepository discoveryRepository;
    private final DiscoveryDataValidator discoveryDataValidator;
    private final CategoryLookup categoryLookup;

    public DiscoveryGetDto add(DiscoveryAddDto dto) {

        discoveryDataValidator.requireUniqueTitle(dto.title());
        discoveryDataValidator.requireUniqueUrl(dto.url());

        //TODO: DiscoveryBusinessValidator?

        Category category = categoryLookup.getEntityById(dto.categoryId());
        Discovery discoveryToAdd = DiscoveryMapper.mapAddDtoToEntity(dto, category);

        Discovery savedDiscovery = discoveryRepository.save(discoveryToAdd);
        CategoryNameDto categoryName = CategoryMapper.mapEntityToNameDto(savedDiscovery.getCategory());

        return DiscoveryMapper.mapEntityToGetDto(savedDiscovery, categoryName);
    }
}
