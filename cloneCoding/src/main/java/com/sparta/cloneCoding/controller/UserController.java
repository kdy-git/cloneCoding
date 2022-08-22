package com.sparta.cloneCoding.controller;

import com.sparta.cloneCoding.dto.UserResponseDto;
import com.sparta.cloneCoding.repository.UserRepository;
import com.sparta.cloneCoding.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyInfo());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponseDto> getMyUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserInfo(username));
    }

    @GetMapping("/idCheck/{username}")
    public boolean idCheck(@PathVariable String username) {
        return !userRepository.existsByUsername(username);
    }

    @GetMapping("/nicknameCheck/{nickname}")
    public boolean nicknameCheck(@PathVariable String nickname) {
        return !userRepository.existsByNickname(nickname);
    }



}