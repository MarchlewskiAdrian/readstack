package com.readstack.crud.vote;

import com.readstack.crud.PageResponse;
import com.readstack.crud.vote.search.VoteFilter;
import com.readstack.crud.vote.search.VoteSpecificationsBuilder;
import com.readstack.dto.VoteGetDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
class VoteFetcher {
    private final VoteRepository voteRepository;
    private final VoteSpecificationsBuilder builder;

    public boolean existsByUserId(Long creator) {
        return voteRepository.existsByAudit_Creator(creator);
    }

    public PageResponse<VoteGetDto> search(VoteFilter filter, Pageable pageable) {
        Specification<Vote> spec = builder.build(filter);
        Page<VoteGetDto> page = voteRepository.findAll(spec, pageable)
                .map(VoteMapper::mapEntityToGetDto);

        return new PageResponse<>(page);
    }

}
