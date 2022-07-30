package com.oursurvey.service.user;

import com.oursurvey.dto.repo.UserDto;

import java.util.Optional;

public interface UserService {
    Optional<UserDto.Basic> findByEmail(String email);

    Long create(UserDto.Create dto);
}
