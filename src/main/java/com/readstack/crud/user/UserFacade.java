package com.readstack.crud.user;

import com.readstack.crud.PageResponse;
import com.readstack.dto.UserAddDto;
import com.readstack.dto.UserGetDto;
import com.readstack.dto.UserUpdateDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UserFacade {
    private final UserAdder userAdder;
    private final UserFetcher userFetcher;
    private final UserUpdater userUpdater;
    private final UserDeleter userDeleter;

    public UserGetDto add(UserAddDto dto){
        return userAdder.add(dto);
    }

    public UserGetDto getById(Long userId){
        return userFetcher.getById(userId);
    }

    public PageResponse<UserGetDto> getAll(Pageable pageable){
        return userFetcher.getAll(pageable);
    }

    public UserGetDto update(Long userId, UserUpdateDto body) {
        return userUpdater.update(userId, body);
    }

    public void deleteById(Long userId) {
        userDeleter.deleteById(userId);
    }
}
