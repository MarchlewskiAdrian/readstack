package com.readstack.controller;

import com.readstack.crud.UserFacade;
import com.readstack.user_crud.UserAddDto;
import com.readstack.user_crud.UserGetDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
