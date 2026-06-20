package com.readstack.crud.vote;

import com.readstack.crud.discovery.Discovery;
import com.readstack.crud.discovery.DiscoveryFacade;
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
    private final DiscoveryFacade discoveryFacade;


    public VoteGetDto add(VoteAddDto dto, SecurityUser user){

        Discovery discovery = discoveryFacade.getEntityById(dto.discoveryId());

        Vote voteToAdd = VoteMapper.mapAddDtoToEntity(dto, user.getUser(), discovery);

        Vote savedVote = voteRepository.save(voteToAdd);

        return VoteMapper.mapEntityToGetDto(savedVote);
    }
}
