package com.sparta.cloneCoding.controller;

import com.sparta.cloneCoding.dto.TokenDto;
import com.sparta.cloneCoding.dto.TokenRequestDto;
import com.sparta.cloneCoding.dto.UserRequestDto;
import com.sparta.cloneCoding.dto.UserResponseDto;
import com.sparta.cloneCoding.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(authService.signup(userRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(authService.login(userRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
}
    @DeleteMapping("/logout")
    public void logout(@RequestBody TokenRequestDto tokenRequestDto) {
        authService.logout(tokenRequestDto);
        }
}
