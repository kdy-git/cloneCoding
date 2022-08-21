package com.sparta.cloneCoding.dto;


import com.sparta.cloneCoding.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private String username;

    public static UserResponseDto of(User user) {
        return new UserResponseDto(user.getUsername());
    }
}
