package com.sparta.cloneCoding.controller;

import com.sparta.cloneCoding.dto.UserResponseDto;
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

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyInfo());
    }

    @GetMapping("/{nickname}")
    public ResponseEntity<UserResponseDto> getMyUserInfo(@PathVariable String nickname) {
        return ResponseEntity.ok(userService.getUserInfo(nickname));
    }
}