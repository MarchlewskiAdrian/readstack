package com.readstack.validation;

import com.readstack.crud.ResourceType;
import com.readstack.crud.user.Role;
import com.readstack.crud.user.User;
import com.readstack.validation.exception.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResourceBusinessValidator {

    public void requireResourceOwnerOrAdmin(User user,
                                            Long creatorId,
                                            ResourceType resourceType) {

        if (isAdmin(user) || isCreator(user.getId(), creatorId)) {
            return;
        }
        throw new AccessDeniedException(
                user.getId(),
                creatorId,
                resourceType
        );

    }

    private boolean isAdmin(User user) {
        String role_admin = Role.ROLE_ADMIN.name();
        return user.getAuthorities().contains(role_admin);
    }

    private boolean isCreator(Long userId, Long creatorId) {
        return userId.equals(creatorId);
    }

}
