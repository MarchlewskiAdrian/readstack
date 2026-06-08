package com.readstack.crud;

import com.readstack.user_crud.User;
import com.readstack.user_crud.UserAddDto;
import com.readstack.user_crud.UserGetDto;

import java.util.HashSet;

class UserMapper {


    public static User mapAddDtoToEntity(UserAddDto dto) {
        return new User(
                dto.username(),
                dto.email(),
                dto.password(),
                true,
                new HashSet<>()
        );
    }

    public static UserGetDto mapEntityToGetDto(User user) {
        return new UserGetDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.isEnabled(),
                user.getAuthorities()
        );
    }
}
