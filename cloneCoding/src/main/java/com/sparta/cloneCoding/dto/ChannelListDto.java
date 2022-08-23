package com.sparta.cloneCoding.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChannelListDto {
    private Long channel_id;
    private String channelName;
    private String description;
    private boolean isOwner;

    public ChannelListDto(Long id, String channelName, String description, boolean isOwner){
        this.channel_id = id;
        this.channelName = channelName;
        this.description = description;
        this.isOwner = isOwner;
    }
}
