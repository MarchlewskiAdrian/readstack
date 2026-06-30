package com.readstack.controller;

import com.readstack.crud.PageResponse;
import com.readstack.crud.discovery.DiscoveryFacade;
import com.readstack.crud.discovery.search.DiscoveryFilter;
import com.readstack.dto.DiscoveryAddDto;
import com.readstack.dto.DiscoveryGetDto;
import com.readstack.security.SecurityUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/discoveries")
@RequiredArgsConstructor
class DiscoveryController {
    private final DiscoveryFacade discoveryFacade;

    @GetMapping
    PageResponse<DiscoveryGetDto> search(DiscoveryFilter filter,
                                         @RequestParam(required = false, defaultValue = "0") Integer page,
                                         @RequestParam(required = false, defaultValue = "20") Integer size,
                                         @RequestParam(required = false, defaultValue = "id") String field,
                                         @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(direction, field));
        return discoveryFacade.search(filter, pageable);
    }


    @GetMapping("/{discoveryId}")
    DiscoveryGetDto getById(@PathVariable Long discoveryId) {
        return discoveryFacade.getById(discoveryId);
    }

    @PostMapping
    ResponseEntity<DiscoveryGetDto> add(@Valid @RequestBody DiscoveryAddDto body) {
        DiscoveryGetDto addedDiscovery = discoveryFacade.add(body);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{discoveryId}")
                .buildAndExpand(addedDiscovery.id())
                .toUri();

        return ResponseEntity.created(uri)
                .body(addedDiscovery);
    }

    @PutMapping("/{discoveryId}")
    DiscoveryGetDto update(
            @PathVariable Long discoveryId,
            @Valid @RequestBody DiscoveryAddDto body,
            @AuthenticationPrincipal SecurityUser securityUser
    ) {
        return discoveryFacade.updateById(discoveryId, body, securityUser);
    }

    @DeleteMapping("/{discoveryId}")
    ResponseEntity<Void> deleteById(@PathVariable Long discoveryId,
                                    @AuthenticationPrincipal SecurityUser securityUser) {
        discoveryFacade.deleteById(discoveryId, securityUser);

        return ResponseEntity.noContent().build();
    }
}
