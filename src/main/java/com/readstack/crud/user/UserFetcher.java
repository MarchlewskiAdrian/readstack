package com.readstack.crud.user;

import com.readstack.crud.PageResponse;
import com.readstack.dto.UserGetDto;
import com.readstack.validation.exception.UserNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
class UserFetcher {
    private final UserRepository userRepository;


    public UserGetDto getById(Long userId){
        return userRepository.findById(userId)
                .map(UserMapper::mapEntityToGetDto)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    public PageResponse<UserGetDto> getAll(Pageable pageable){
        Page<UserGetDto> page = userRepository.findAll(pageable)
                .map(UserMapper::mapEntityToGetDto);
        return new PageResponse<>(page);
    }

    public User getEntityById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }
}
