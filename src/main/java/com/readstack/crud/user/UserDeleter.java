package com.readstack.crud.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
class UserDeleter {
    private final UserRepository userRepository;
    private final UserDataValidator userDataValidator;
    private final UserBusinessValidator userBusinessValidator;

    public void deleteById(Long userId) {

        userDataValidator.requireUserExists(userId);
        userBusinessValidator.requireNoRelatedDiscoveries(userId);
        userBusinessValidator.requireNoRelatedVotes(userId);

        userRepository.deleteById(userId);
    }

}
