package com.readstack.crud.vote;

import com.readstack.validation.exception.DuplicateVoteException;
import com.readstack.validation.exception.SelfVoteNotAllowedException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class VoteBusinessValidator {
    private final VoteRepository voteRepository;

    void requireNotVoteCreator(Long creatorId, Long userId){
        if (creatorId.equals(userId)) {
            throw new SelfVoteNotAllowedException();
        }
    }
    void requireSingleVote(Long userId, Long discoveryId){
        if (voteRepository.existsByUserIdAndDiscoveryId(userId, discoveryId)) {
            throw new DuplicateVoteException();
        }
    }
}
