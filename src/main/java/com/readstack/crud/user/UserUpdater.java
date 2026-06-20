package com.readstack.crud.user;

import com.readstack.dto.UserGetDto;
import com.readstack.dto.UserUpdateDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
class UserUpdater {
    private final UserFetcher userFetcher;

    public UserGetDto update(Long userId, UserUpdateDto body) {
        User user = userFetcher.getEntityById(userId);

        user.setUsername(body.username());
        user.setEmail(body.email());
        user.setEnabled(body.enabled());
        user.setAuthorities(body.roles());

        return UserMapper.mapEntityToGetDto(user);
    }

}
