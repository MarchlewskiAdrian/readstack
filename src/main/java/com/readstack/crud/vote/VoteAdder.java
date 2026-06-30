package com.readstack.crud.vote;

import com.readstack.crud.discovery.Discovery;
import com.readstack.crud.discovery.DiscoveryLookup;
import com.readstack.dto.VoteAddDto;
import com.readstack.dto.VoteGetDto;
import com.readstack.security.SecurityUser;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class VoteAdder {
    private final VoteRepository voteRepository;
    private final DiscoveryLookup discoveryLookup;
    private final VoteBusinessValidator voteBusinessValidator;

    public VoteGetDto add(VoteAddDto dto, SecurityUser user) {

        Discovery discovery = discoveryLookup.getEntityById(dto.discoveryId()); //TODO: rozbicie na wyciaganie creator i dodanie glosu?
        Long creatorId = discovery.getAudit().getCreator();
        Long userId = user.getUser().getId();

        voteBusinessValidator.requireNotVoteCreator(creatorId, userId);
        voteBusinessValidator.requireSingleVote(userId, discovery.getId());

        discovery.addVote();
        Vote voteToAdd = VoteMapper.mapAddDtoToEntity(dto, user.getUser(), discovery);
        Vote savedVote = voteRepository.save(voteToAdd);

        return VoteMapper.mapEntityToGetDto(savedVote);
    }
}
