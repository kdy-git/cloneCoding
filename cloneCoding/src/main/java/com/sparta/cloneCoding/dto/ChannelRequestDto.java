package com.sparta.cloneCoding.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelRequestDto {
    private String channelName;
    private String description;

    public ChannelRequestDto(String channelName, String description){
        this.channelName = channelName;
        this.description = description;
    }
}
