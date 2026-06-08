package com.readstack.crud;

import com.readstack.user_crud.UserAddDto;
import com.readstack.user_crud.UserGetDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UserFacade {
    private final UserAdder userAdder;

    public UserGetDto add(UserAddDto dto){
        return userAdder.add(dto);

    }
}
