package com.readstack.controller;

import com.readstack.crud.CategoryAddDto;
import com.readstack.crud.CategoryGetDto;
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
@RequestMapping("/categories")
class CategoryController {
    private final Facade facade;


    @GetMapping
    public PageResponse<CategoryGetDto> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false, defaultValue = "id") String field,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, field));
        return facade.getAllCategories(pageable);
    }

    @GetMapping("/{categoryId}")
    public CategoryGetDto getById(@PathVariable Long categoryId) {
        return facade.getCategoryById(categoryId);
    }

    @PutMapping("/{categoryId}")
    public CategoryGetDto updateById(
            @PathVariable Long categoryId,
            @RequestBody CategoryAddDto body
    ) {
        return facade.updateCategoryById(categoryId, body);
    }

    @PostMapping
    public ResponseEntity<CategoryGetDto> add(@RequestBody CategoryAddDto body) {
        CategoryGetDto addedCategory = facade.addCategory(body);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{categoryId}")
                .buildAndExpand(addedCategory.id())
                .toUri();

        return ResponseEntity.created(uri)
                .body(addedCategory);

    }

    @DeleteMapping("/{categoryId}")
    public void deleteById(@PathVariable Long categoryId) {
        facade.deleteCategoryById(categoryId);
    }
}

