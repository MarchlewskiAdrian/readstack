package com.readstack.crud.discovery;

import com.readstack.crud.PageResponse;
import com.readstack.crud.discovery.search.DiscoveryFilter;
import com.readstack.dto.DiscoveryAddDto;
import com.readstack.dto.DiscoveryGetDto;
import com.readstack.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscoveryFacade {
    private final DiscoveryFetcher discoveryFetcher;
    private final DiscoveryAdder discoveryAdder;
    private final DiscoveryUpdater discoveryUpdater;
    private final DiscoveryDeleter discoveryDeleter;


    public PageResponse<DiscoveryGetDto> search(DiscoveryFilter filter, Pageable pageable) {
        return discoveryFetcher.search(filter, pageable);
    }

    public PageResponse<DiscoveryGetDto> getAllByCategoryId(Long categoryId, Pageable pageable) {
        return discoveryFetcher.getAllByCategoryId(categoryId, pageable);
    }

    public DiscoveryGetDto getById(Long id) {
        return discoveryFetcher.getById(id);
    }

    public DiscoveryGetDto add(DiscoveryAddDto body) {
        return discoveryAdder.add(body);
    }

    public DiscoveryGetDto updateById(Long discoveryId, DiscoveryAddDto body, SecurityUser securityUser) {
        return discoveryUpdater.updateById(discoveryId, body, securityUser);
    }

    public void deleteById(Long discoveryId, SecurityUser securityUser) {
        discoveryDeleter.deleteById(discoveryId, securityUser);
    }

    public Discovery getEntityById(Long discoveryId) {
        return discoveryFetcher.getEntityById(discoveryId);
    }

}
