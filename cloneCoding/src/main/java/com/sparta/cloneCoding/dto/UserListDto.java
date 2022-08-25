package com.sparta.cloneCoding.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserListDto {
    private Long userId;
    private String username;
    private String nickname;

    public UserListDto (Long id, String username, String nickname){
        this.userId = id;
        this.username = username;
        this.nickname = nickname;
    }
}