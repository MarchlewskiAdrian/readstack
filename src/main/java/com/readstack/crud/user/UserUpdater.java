package com.readstack.crud.user;

import com.readstack.crud.ResourceType;
import com.readstack.dto.UserGetDto;
import com.readstack.dto.UserUpdateDto;
import com.readstack.security.SecurityUser;
import com.readstack.validation.ResourceBusinessValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class UserUpdater {
    private final UserFetcher userFetcher;
    private final UserDataValidator userDataValidator;
    private final ResourceBusinessValidator resourceBusinessValidator;

    public UserGetDto update(Long userId,
                             UserUpdateDto dto,
                             SecurityUser securityUser) {

        User entity = userFetcher.getEntityById(userId);
        User user = securityUser.getUser();
        Long creatorId = entity.getAudit().getCreator();

        resourceBusinessValidator.requireResourceOwnerOrAdmin(user, creatorId, ResourceType.USER);
        userDataValidator.requireUniqueUsername(userId, dto.username());
        userDataValidator.requireUniqueEmail(userId, dto.email());

        entity.setUsername(dto.username());
        entity.setEmail(dto.email());
        entity.setEnabled(dto.enabled());
        entity.setAuthorities(dto.roles());

        return UserMapper.mapEntityToGetDto(entity);
    }

}
