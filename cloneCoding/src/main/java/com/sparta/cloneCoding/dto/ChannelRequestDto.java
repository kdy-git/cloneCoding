package com.sparta.cloneCoding.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelRequestDto {
    private String channelName;
    private String description;
    private Boolean isPrivate;

    public ChannelRequestDto(String channelName, String description, Boolean isPrivate){
        this.channelName = channelName;
        this.description = description;
        this.isPrivate = isPrivate;
    }
}
