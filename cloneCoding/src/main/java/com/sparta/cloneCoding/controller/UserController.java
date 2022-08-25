package com.sparta.cloneCoding.controller;

import com.sparta.cloneCoding.repository.UserRepository;
import com.sparta.cloneCoding.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/idCheck/{username}")
    public boolean idCheck(@PathVariable String username) {
        return !userRepository.existsByUsername(username);
    }

    @GetMapping("/nicknameCheck/{nickname}")
    public boolean nicknameCheck(@PathVariable String nickname) {
        return !userRepository.existsByNickname(nickname);
    }

}