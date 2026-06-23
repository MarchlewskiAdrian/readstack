package com.readstack.controller;

import com.readstack.crud.PageResponse;
import com.readstack.crud.vote.VoteFacade;
import com.readstack.crud.vote.search.VoteFilter;
import com.readstack.dto.VoteAddDto;
import com.readstack.dto.VoteGetDto;
import com.readstack.security.SecurityUser;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/votes")
class VoteController {
    private final VoteFacade voteFacade;

    @PostMapping
    ResponseEntity<VoteGetDto> addVote(@RequestBody VoteAddDto body,
                                       @AuthenticationPrincipal SecurityUser user){
        VoteGetDto addedVote = voteFacade.add(body, user);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{voteId}")
                .buildAndExpand(addedVote.id())
                .toUri();

        return ResponseEntity.created(uri).body(addedVote);
    }

    @GetMapping
    PageResponse<VoteGetDto> search(VoteFilter filter,
                                    @RequestParam(required = false, defaultValue = "0") Integer page,
                                    @RequestParam(required = false, defaultValue = "20") Integer size,
                                    @RequestParam(required = false, defaultValue = "id") String field,
                                    @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction
                                    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, field));

        return voteFacade.search(filter, pageable);
    }
}
