package com.readstack.crud.discovery;

import com.readstack.crud.PageResponse;
import com.readstack.dto.DiscoveryGetDto;
import org.springframework.data.domain.Pageable;

public interface DiscoveryLookup {
    boolean existsByCategoryId(Long categoryId);

    PageResponse<DiscoveryGetDto> getAllByCategoryId(Long categoryId, Pageable pageable);
}
