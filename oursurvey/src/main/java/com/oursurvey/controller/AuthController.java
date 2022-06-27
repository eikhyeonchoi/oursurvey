package com.oursurvey.controller;

import com.oursurvey.dto.AuthDto;
import com.oursurvey.dto.MyResponse;
import com.oursurvey.dto.TokenDto;
import com.oursurvey.dto.repo.user.UserDto;
import com.oursurvey.exception.AuthFailException;
import com.oursurvey.exception.InvalidFormException;
import com.oursurvey.service.user.UserService;
import com.oursurvey.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService service;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, Object> redis;

    @Value("${spring.redis.prefix.key}")
    private String REDIS_PREFIX_KEY;

    @PostMapping("/login")
    public MyResponse login(@Validated @RequestBody AuthDto.Login dto, BindingResult br) throws Exception {
        MyResponse res = new MyResponse();
        if (br.hasFieldErrors()) {
            throw new InvalidFormException("invalid form");
        }

        Optional<UserDto.Basic> opt = service.findByEmail(dto.getEmail());
        if (opt.isEmpty()) {
            throw new AuthFailException("invalid id or pass");
        }

        UserDto.Basic user = opt.get();
        if (!encoder.matches(dto.getPwd(), user.getPwd())) {
            throw new AuthFailException("invalid id or pass");
        }

        TokenDto token = jwtUtil.createToken(user.getId());
        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("tokenType", "Bearer");
        dataMap.put("access", token.getAccessToken());
        dataMap.put("refresh", token.getRefreshToken());
        dataMap.put("refreshExpire", token.getRefreshTokenExpire());

        // redis
        ValueOperations<String, Object> vop = redis.opsForValue();
        vop.set(REDIS_PREFIX_KEY + user.getId(), token.getRefreshToken(), JwtUtil.REFRESH_TOKEN_PERIOD, TimeUnit.SECONDS);

        return res;
    }
}
