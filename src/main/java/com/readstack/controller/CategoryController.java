package com.readstack.controller;

import com.readstack.crud.category.CategoryFacade;
import com.readstack.crud.PageResponse;
import com.readstack.dto.CategoryAddDto;
import com.readstack.dto.CategoryGetDto;
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
@RequiredArgsConstructor
@RequestMapping("/categories")
class CategoryController {
    private final CategoryFacade categoryFacade;

    @GetMapping
    public PageResponse<CategoryGetDto> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false, defaultValue = "id") String field,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, field));
        return categoryFacade.getAll(pageable);
    }

    @GetMapping("/{categoryId}")
    public CategoryGetDto getById(@PathVariable Long categoryId) {
        return categoryFacade.getById(categoryId);
    }

    @GetMapping("/{categoryId}/discoveries")
    public  PageResponse<DiscoveryGetDto> getDiscoveriesByCategoryId(@PathVariable Long categoryId,
                                                                     @RequestParam(required = false, defaultValue = "0") Integer page,
                                                                     @RequestParam(required = false, defaultValue = "20") Integer size,
                                                                     @RequestParam(required = false, defaultValue = "id") String field,
                                                                     @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction){
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, field));

        return categoryFacade.getDiscoveriesByCategoryId(categoryId, pageable);
    }

    @PutMapping("/{categoryId}")
    public CategoryGetDto updateById(
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryAddDto body
    ) {
        return categoryFacade.updateById(categoryId, body);
    }

    @PostMapping
    public ResponseEntity<CategoryGetDto> add(@Valid @RequestBody CategoryAddDto body) {
        CategoryGetDto addedCategory = categoryFacade.add(body);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{categoryId}")
                .buildAndExpand(addedCategory.id())
                .toUri();

        return ResponseEntity.created(uri)
                .body(addedCategory);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long categoryId) {
        categoryFacade.deleteById(categoryId);

        return ResponseEntity.noContent()
                .build();
    }
}

