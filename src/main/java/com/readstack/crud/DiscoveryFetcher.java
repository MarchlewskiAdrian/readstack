package com.readstack.crud;

import com.readstack.dto.CategoryNameDto;
import com.readstack.dto.DiscoveryGetDto;
import com.readstack.validation.exception.DiscoveryNotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DiscoveryFetcher {
    private final DiscoveryRepository discoveryRepository;

    public PageResponse<DiscoveryGetDto> getAll(String title, Pageable pageable) {
        Page<DiscoveryGetDto> page = discoveryRepository.findAll(title, pageable)
                .map(this::toGetDto);

        return toPageResponse(page);
    }

    public PageResponse<DiscoveryGetDto> getAllByCategoryId(Long categoryId, Pageable pageable) {
        Page<DiscoveryGetDto> page = discoveryRepository.findAllByCategory_Id(categoryId, pageable)
                .map(this::toGetDto);

        return toPageResponse(page);
    }

    public boolean existsByCategoryId(Long categoryId){
        return discoveryRepository.existsByCategory_Id(categoryId);
    }

    public DiscoveryGetDto getById(Long id) {
        return discoveryRepository.findById(id)
                .map(this::toGetDto)
                .orElseThrow(() -> new DiscoveryNotFoundException(id));
    }

    public Discovery getEntityById(Long id){
        return discoveryRepository.findById(id)
                .orElseThrow(() -> new DiscoveryNotFoundException(id));
    }
    private DiscoveryGetDto toGetDto(Discovery discovery) {
        CategoryNameDto categoryNameDto = CategoryMapper.mapEntityToNameDto(discovery.getCategory());
        return DiscoveryMapper.mapEntityToGetDto(discovery, categoryNameDto);
    }

    private PageResponse<DiscoveryGetDto> toPageResponse(Page<DiscoveryGetDto> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }
}


