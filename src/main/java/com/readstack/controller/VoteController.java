package com.readstack.controller;

import com.readstack.crud.PageResponse;
import com.readstack.crud.vote.VoteFacade;
import com.readstack.dto.VoteAddDto;
import com.readstack.dto.VoteGetDto;
import com.readstack.security.SecurityUser;
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
@RequestMapping("/votes")
class VoteController {
    private final VoteFacade voteFacade;

    @PostMapping
    ResponseEntity<VoteGetDto> addVote(@RequestBody VoteAddDto body, @AuthenticationPrincipal SecurityUser user){
        VoteGetDto addedVote = voteFacade.add(body, user);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{voteId}")
                .buildAndExpand(addedVote.id())
                .toUri();

        return ResponseEntity.created(uri).body(addedVote);
    }

    @GetMapping
    PageResponse<VoteGetDto> getAll(@RequestParam(required = false) Long discoveryId,
                                    @RequestParam(required = false) Long userId,
                                    @RequestParam(required = false, defaultValue = "0") Integer page,
                                    @RequestParam(required = false, defaultValue = "20") Integer size){
        Pageable pageable = PageRequest.of(page, size);

        return voteFacade.getAll(discoveryId, userId, pageable); //TODO: Add specificatoin
    }
}
