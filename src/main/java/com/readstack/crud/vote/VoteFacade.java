package com.readstack.crud.vote;

import com.readstack.crud.PageResponse;
import com.readstack.crud.vote.search.VoteFilter;
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

    public PageResponse<VoteGetDto> search(VoteFilter filter, Pageable pageable) {
        return voteFetcher.search(filter, pageable);
    }

    public boolean hasUserVotes(Long userId) {
        return voteFetcher.existsByUserId(userId);
    }
}
