package com.readstack.crud.vote;

import com.readstack.crud.PageResponse;
import com.readstack.dto.VoteAddDto;
import com.readstack.dto.VoteGetDto;
import com.readstack.security.SecurityUser;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class VoteFacade {
    private final VoteAdder voteAdder;
    private final VoteFetcher voteFetcher;

    public VoteGetDto add(VoteAddDto dto, SecurityUser user) {
        return voteAdder.add(dto, user);
    }

    public PageResponse<VoteGetDto> getAll(Long discoveryId, Long userId, Pageable pageable) {
        return voteFetcher.getAll(discoveryId, userId, pageable);
    }

    public boolean hasUserVotes(Long userId) {
        return voteFetcher.existsByUserId(userId);
    }
}
