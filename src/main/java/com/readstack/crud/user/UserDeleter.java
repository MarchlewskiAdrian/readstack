package com.readstack.crud.user;

import com.readstack.crud.discovery.DiscoveryFacade;
import com.readstack.crud.vote.VoteFacade;
import com.readstack.validation.exception.UserNotFoundException;
import com.readstack.validation.exception.UserHasRelatedDataException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
class UserDeleter {
    private final UserRepository userRepository;
    private final DiscoveryFacade discoveryFacade;
    private final VoteFacade voteFacade;

    private final static String DISCOVERIES = "discoveries";
    private final static String VOTES = "votes";

    public void deleteById(Long userId) {
        if (!userRepository.existsById(userId)){
            throw new UserNotFoundException(userId);
        }
        if (voteFacade.hasUserVotes(userId)){
            throw new UserHasRelatedDataException(userId, DISCOVERIES);
        }
        if (discoveryFacade.hasUserDiscoveries(userId)){
            throw new UserHasRelatedDataException(userId, VOTES);
        }
        userRepository.deleteById(userId);

    }

}
