package com.sparta.cloneCoding.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChannelInviteRequestDto {
    private Long channel_id;
    private String inviteUser;
}
