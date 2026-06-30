package com.readstack.controller;

import com.readstack.crud.PageResponse;
import com.readstack.crud.user.UserFacade;
import com.readstack.dto.UserAddDto;
import com.readstack.dto.UserGetDto;
import com.readstack.dto.UserUpdateDto;
import com.readstack.security.SecurityUser;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
class UserController {
    private final UserFacade userFacade;

    @PostMapping
    public ResponseEntity<UserGetDto> add(@Valid @RequestBody UserAddDto body){
        UserGetDto addedUser = userFacade.add(body);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{userId}")
                .buildAndExpand(addedUser.id())
                .toUri();

        return ResponseEntity.created(uri)
                .body(addedUser);

    }
    @GetMapping
    PageResponse<UserGetDto> getAll(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return userFacade.getAll(pageable);
    }

    @GetMapping("/{userId}")
    public UserGetDto getById(@PathVariable Long userId){
        return userFacade.getById(userId);
    }

    @PutMapping("/{userId}")
    public UserGetDto update(@PathVariable Long userId,
                             @RequestBody UserUpdateDto body,
                             @AuthenticationPrincipal SecurityUser securityUser){
        return userFacade.update(userId, body, securityUser);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> delete(@PathVariable Long userId){
        userFacade.deleteById(userId);

        return ResponseEntity.noContent()
                .build();
    }

}
