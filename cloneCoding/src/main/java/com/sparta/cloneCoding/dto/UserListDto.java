package com.sparta.cloneCoding.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserListDto {
    private Long user_id;
    private String username;
    private String nickname;
//     일단 프로필 이미지 제외
//    private String imgUrl;

    public UserListDto (Long id, String username, String nickname){
        this.user_id = id;
        this.username = username;
        this.nickname = nickname;
//        this.imgUrl = imgUrl;
    }
}
