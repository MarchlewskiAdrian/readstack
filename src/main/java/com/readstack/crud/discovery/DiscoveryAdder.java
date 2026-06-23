package com.readstack.crud.discovery;

import com.readstack.crud.category.Category;
import com.readstack.crud.category.CategoryFacade;
import com.readstack.crud.category.CategoryMapper;
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
    private final DiscoveryValidator discoveryValidator;

    private final CategoryFacade categoryFacade;

    public DiscoveryGetDto add(DiscoveryAddDto dto) {
        discoveryValidator.validateForAdd(dto);

        Category category = categoryFacade.getEntityById(dto.categoryId());
        Discovery discoveryToAdd = DiscoveryMapper.mapAddDtoToEntity(dto, category);

        Discovery savedDiscovery = discoveryRepository.save(discoveryToAdd);
        CategoryNameDto categoryName = CategoryMapper.mapEntityToNameDto(savedDiscovery.getCategory());
        return DiscoveryMapper.mapEntityToGetDto(savedDiscovery, categoryName);
    }
}
