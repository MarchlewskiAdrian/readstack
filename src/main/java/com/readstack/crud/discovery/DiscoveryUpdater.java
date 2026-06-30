package com.readstack.crud.discovery;

import com.readstack.crud.ResourceType;
import com.readstack.crud.category.Category;
import com.readstack.crud.category.CategoryLookup;
import com.readstack.crud.category.CategoryMapper;
import com.readstack.crud.user.User;
import com.readstack.dto.CategoryNameDto;
import com.readstack.dto.DiscoveryAddDto;
import com.readstack.dto.DiscoveryGetDto;
import com.readstack.security.SecurityUser;
import com.readstack.validation.ResourceBusinessValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DiscoveryUpdater {
    private final DiscoveryFetcher discoveryFetcher;
    private final DiscoveryRepository discoveryRepository;
    private final DiscoveryDataValidator discoveryDataValidator;
    private final ResourceBusinessValidator resourceBusinessValidator;
    private final CategoryLookup categoryLookup;

    public DiscoveryGetDto updateById(Long discoveryId,
                                      DiscoveryAddDto dto,
                                      SecurityUser securityUser) {

        Discovery discovery = discoveryFetcher.getEntityById(discoveryId);
        Category category = categoryLookup.getEntityById(dto.categoryId());

        User user = securityUser.getUser();
        Long creatorId = discovery.getAudit()
                .getCreator();

        resourceBusinessValidator.requireResourceOwnerOrAdmin(user, creatorId, ResourceType.DISCOVERY);
        discoveryDataValidator.requireUniqueTitle(discoveryId, dto.title());
        discoveryDataValidator.requireUniqueUrl(discoveryId, dto.url());

        CategoryNameDto categoryNameDto = CategoryMapper.mapEntityToNameDto(category);

        discovery.setTitle(dto.title());
        discovery.setUrl(dto.url());
        discovery.setDescription(dto.description());
        discovery.setCategory(category);

        Discovery savedDiscovery = discoveryRepository.save(discovery);
        return DiscoveryMapper.mapEntityToGetDto(savedDiscovery, categoryNameDto);

    }
}
