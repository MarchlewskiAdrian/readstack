package com.readstack.crud.user;

import com.readstack.crud.ResourceType;
import com.readstack.validation.exception.ResourceExistsException;
import com.readstack.validation.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class UserDataValidator {
    private final UserRepository userRepository;

    void requireUserExists(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(userId, ResourceType.USER);
        }
    }

    public void requireUniqueUsername(Long userId, String username) {
        if (usernameExists(userId, username)) {
            throw new ResourceExistsException("username", username, ResourceType.USER);
        }
    }

    void requireUniqueUsername(String username) {
        if (usernameExists(null, username)) {
            throw new ResourceExistsException("username", username, ResourceType.USER);
        }
    }

    public void requireUniqueEmail(Long userId, String email) {
        if (emailExists(userId, email)) {
            throw new ResourceExistsException("email", email, ResourceType.USER);
        }
    }

    void requireUniqueEmail(String email) {
        if (emailExists(null, email)) {
            throw new ResourceExistsException("email", email, ResourceType.USER);
        }
    }

    private boolean usernameExists(Long userId, String username) {
        return userId == null ?
                userRepository.existsByUsernameIgnoreCase(username) :
                userRepository.existsByUsernameIgnoreCaseAndIdNot(username, userId);
    }

    private boolean emailExists(Long userId, String email) {
        return userId == null ?
                userRepository.existsByEmailIgnoreCase(email) :
                userRepository.existsByEmailIgnoreCaseAndIdNot(email, userId);
    }
}
