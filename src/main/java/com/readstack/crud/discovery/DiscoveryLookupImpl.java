package com.readstack.crud.discovery;

import com.readstack.crud.PageResponse;
import com.readstack.dto.DiscoveryGetDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiscoveryLookupImpl implements DiscoveryLookup {
    private final DiscoveryFetcher discoveryFetcher;

    @Override
    public boolean existsByCategoryId(Long categoryId) {
        return discoveryFetcher.existsByCategoryId(categoryId);
    }

    @Override
    public PageResponse<DiscoveryGetDto> getAllByCategoryId(Long categoryId, Pageable pageable) {
        return discoveryFetcher.getAllByCategoryId(categoryId, pageable);
    }

    @Override
    public boolean hasUserDiscoveries(Long userId) {
        return discoveryFetcher.existsByUserId(userId);
    }

    @Override
    public Discovery getEntityById(Long discoveryId) {
        return discoveryFetcher.getEntityById(discoveryId);
    }
}
