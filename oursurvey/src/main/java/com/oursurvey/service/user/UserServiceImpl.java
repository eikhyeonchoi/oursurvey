package com.oursurvey.service.user;

import com.oursurvey.dto.repo.user.UserDto;
import com.oursurvey.entity.User;
import com.oursurvey.repo.user.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Primary
public class UserServiceImpl implements UserService {
    private final UserRepo repo;

    @Override
    public Optional<UserDto.Basic> findByEmail(String email) {
        Optional<User> opt = repo.findByEmail(email);
        if (opt.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(UserDto.Basic.builder().entity(opt.get()).build());
    }
}