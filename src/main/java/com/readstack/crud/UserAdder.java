package com.readstack.crud;

import com.readstack.user_crud.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class UserAdder {
    private static final String DEFAULT_ROLE = Role.ROLE_USER.name();

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserGetDto add(UserAddDto dto) {
        //TODO: userValidatior.validateForAdd(dto);

        String encodedPassword = passwordEncoder.encode(dto.password());

        User userToAdd = UserMapper.mapAddDtoToEntity(dto);
        userToAdd.setPassword(encodedPassword);
        userToAdd.addRole(DEFAULT_ROLE);
        User savedUser = userRepository.save(userToAdd);


        return UserMapper.mapEntityToGetDto(savedUser);
    }
}
