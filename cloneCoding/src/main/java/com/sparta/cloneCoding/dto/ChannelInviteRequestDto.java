package com.sparta.cloneCoding.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ChannelInviteRequestDto {
    private Long channelId;
    private List<Long> userList;
}
