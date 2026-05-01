package com.readstack.controller;

import com.readstack.crud.DiscoveryAddDto;
import com.readstack.crud.DiscoveryGetDto;
import com.readstack.crud.Facade;
import com.readstack.crud.PageResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DiscoveryController {
    private final Facade facade;


    @GetMapping("/discoveries")
    PageResponse<DiscoveryGetDto> getAll(
            @RequestParam(required = false) String title,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false, defaultValue = "id") String field,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction

    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, field));

        return facade.getAllDiscoveries(title, pageable);
    }

    @GetMapping("/categories/{categoryId}/discoveries")
    PageResponse<DiscoveryGetDto> getAllByCategory
            (@PathVariable Long categoryId,
             @RequestParam(required = false, defaultValue = "0") Integer page,
             @RequestParam(required = false, defaultValue = "20") Integer size,
             @RequestParam(required = false, defaultValue = "id") String field,
             @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction
            ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, field));

        return facade.getAllDiscoveriesByCategoryId(categoryId, pageable);

    }

    @GetMapping("/discoveries/{discoveryId}")
    DiscoveryGetDto getById(@PathVariable Long discoveryId) {
        return facade.getDiscoveryById(discoveryId);
    }

    //location: http://localhost:8080/discoveries/101?title=string&url=string&description=string&categoryId=1 
    @PostMapping("/discoveries")
    ResponseEntity<DiscoveryGetDto> add(DiscoveryAddDto body) {
        DiscoveryGetDto addedDiscovery = facade.addDiscovery(body);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{discoveryId}")
                .buildAndExpand(addedDiscovery.id())
                .toUri();

        return ResponseEntity.created(uri)
                .body(addedDiscovery);
    }

    @PutMapping("/discoveries/{discoveryId}")
    DiscoveryGetDto update(
            @PathVariable Long discoveryId,
            @RequestBody DiscoveryAddDto body
    ) {
        return facade.updateDiscoveryById(discoveryId, body);
    }

    @DeleteMapping("/discoveries/{discoveryId}")
    void deleteById(@PathVariable Long discoveryId) {
        facade.deleteDiscoveryById(discoveryId);
    }
}
