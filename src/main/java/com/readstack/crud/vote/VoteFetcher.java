package com.readstack.crud.vote;

import com.readstack.crud.PageResponse;
import com.readstack.dto.VoteGetDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
class VoteFetcher {
    private final VoteRepository voteRepository;

    public boolean existsByUserId(Long creator) {
        return voteRepository.existsByAudit_creator(creator);
    }

    public PageResponse<VoteGetDto> getAll(Long discoveryId, Long userId, Pageable pageable) {
        Page<Vote> votes;

        if (discoveryId != null && userId != null) {
            votes = voteRepository.findAllByUser_IdAndDiscovery_Id(userId, discoveryId, pageable);
        }
        else if (discoveryId != null){
            votes = voteRepository.findAllByDiscovery_Id(discoveryId, pageable);
        }
        else if (userId != null){
            votes = voteRepository.findAllByUser_Id(userId, pageable);
        }
        else {
            votes = voteRepository.findAll(pageable);
        }

        Page<VoteGetDto> votesDto = votes.map(VoteMapper::mapEntityToGetDto);

        return toPageResponse(votesDto);
    }

    public static PageResponse<VoteGetDto> toPageResponse(Page<VoteGetDto> page) { //TODO: Util method
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                page.getTotalElements()
        );
    }
}
