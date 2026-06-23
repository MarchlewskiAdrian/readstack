package com.readstack.crud.discovery;

import com.readstack.crud.category.CategoryMapper;
import com.readstack.crud.PageResponse;
import com.readstack.crud.discovery.search.DiscoveryFilter;
import com.readstack.crud.discovery.search.DiscoverySpecificationsBuilder;
import com.readstack.dto.CategoryNameDto;
import com.readstack.dto.DiscoveryGetDto;
import com.readstack.validation.exception.DiscoveryNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DiscoveryFetcher {
    private final DiscoveryRepository discoveryRepository;
    private final DiscoverySpecificationsBuilder specificationsBuilder;


    public PageResponse<DiscoveryGetDto> search(DiscoveryFilter filter, Pageable pageable) {
        Specification<Discovery> spec = specificationsBuilder.build(filter);
        Page<DiscoveryGetDto> page = discoveryRepository.findAll(spec, pageable)
                .map(this::toGetDto);

        return new PageResponse<>(page);
    }

    public PageResponse<DiscoveryGetDto> getAllByCategoryId(Long categoryId, Pageable pageable) {
        Page<DiscoveryGetDto> page = discoveryRepository.findAllByCategory_Id(categoryId, pageable)
                .map(this::toGetDto);

        return new PageResponse<>(page);
    }

    public boolean existsByCategoryId(Long categoryId) {
        return discoveryRepository.existsByCategory_Id(categoryId);
    }

    public boolean existsByUserId(Long userId) {
        return discoveryRepository.existsByAudit_creator(userId);
    }

    public DiscoveryGetDto getById(Long id) {
        return discoveryRepository.findById(id)
                .map(this::toGetDto)
                .orElseThrow(() -> new DiscoveryNotFoundException(id));
    }

    public Discovery getEntityById(Long id) {
        return discoveryRepository.findById(id)
                .orElseThrow(() -> new DiscoveryNotFoundException(id));
    }

    private DiscoveryGetDto toGetDto(Discovery discovery) {
        CategoryNameDto categoryNameDto = CategoryMapper.mapEntityToNameDto(discovery.getCategory());
        return DiscoveryMapper.mapEntityToGetDto(discovery, categoryNameDto);
    }

}


