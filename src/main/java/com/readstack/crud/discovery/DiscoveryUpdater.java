package com.readstack.crud.discovery;

import com.readstack.crud.category.Category;
import com.readstack.crud.category.CategoryFetcher;
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
class DiscoveryUpdater {

    private final DiscoveryFetcher discoveryFetcher;
    private final DiscoveryRepository discoveryRepository;
    private final CategoryFetcher categoryFetcher;
    private final DiscoveryValidator discoveryValidator;

    public DiscoveryGetDto updateById(Long id, DiscoveryAddDto dto) {

        discoveryValidator.validateForUpdate(id, dto);

        Discovery discovery = discoveryFetcher.getEntityById(id);
        Category category = categoryFetcher.getEntityById(dto.categoryId());
        CategoryNameDto categoryNameDto = CategoryMapper.mapEntityToNameDto(category); //TODO: Don't import CategoryMaper, add facade method getCategoryName

        discovery.setTitle(dto.title());
        discovery.setUrl(dto.url());
        discovery.setDescription(dto.description());
        discovery.setCategory(category);

        Discovery savedDiscovery = discoveryRepository.save(discovery);
        return DiscoveryMapper.mapEntityToGetDto(savedDiscovery, categoryNameDto);

    }
}
