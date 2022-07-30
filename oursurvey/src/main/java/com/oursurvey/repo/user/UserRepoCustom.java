package com.oursurvey.repo.user;

import com.oursurvey.entity.User;

import java.util.Optional;

public interface UserRepoCustom {
    Optional<User> findByEmail(String email);
}
