package com.readstack.controller;

import com.readstack.crud.discovery.DiscoveryFacade;
import com.readstack.crud.PageResponse;
import com.readstack.dto.DiscoveryAddDto;
import com.readstack.dto.DiscoveryGetDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/discoveries")
@RequiredArgsConstructor
class DiscoveryController {
    private final DiscoveryFacade discoveryFacade;

    @GetMapping
    PageResponse<DiscoveryGetDto> getAll(
            @RequestParam(required = false) String title,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false, defaultValue = "id") String field,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction

    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, field));
        return discoveryFacade.getAll(title, pageable);
    }

    @GetMapping("/categories/{categoryId}")
    PageResponse<DiscoveryGetDto> getAllByCategoryId(
            @PathVariable Long categoryId,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false, defaultValue = "id") String field,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, field));
        return discoveryFacade.getAllByCategoryId(categoryId, pageable);
    }

    @GetMapping("/{discoveryId}")
    DiscoveryGetDto getById(@PathVariable Long discoveryId) {
        return discoveryFacade.getById(discoveryId);
    }

    //location: http://localhost:8080/discoveries/101?title=string&url=string&description=string&categoryId=1
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
            @Valid @RequestBody DiscoveryAddDto body
    ) {
        return discoveryFacade.updateById(discoveryId, body);
    }

    @DeleteMapping("/{discoveryId}")
    ResponseEntity<Void> deleteById(@PathVariable Long discoveryId) {
        discoveryFacade.deleteById(discoveryId);

        return ResponseEntity.noContent().build();
    }
}
