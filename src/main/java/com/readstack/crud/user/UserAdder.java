package com.readstack.crud.user;

import com.readstack.dto.UserAddDto;
import com.readstack.dto.UserGetDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class UserAdder {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDataValidator userDataValidator;

    public UserGetDto add(UserAddDto dto) {

        userDataValidator.requireUniqueUsername(dto.username());
        userDataValidator.requireUniqueEmail(dto.email());

        User userToAdd = UserMapper.mapAddDtoToEntity(dto);
        String encodedPassword = passwordEncoder.encode(dto.password());
        userToAdd.setPassword(encodedPassword);
        userToAdd.addRole(Role.ROLE_USER.name());

        User savedUser = userRepository.save(userToAdd);

        return UserMapper.mapEntityToGetDto(savedUser);
    }
}
