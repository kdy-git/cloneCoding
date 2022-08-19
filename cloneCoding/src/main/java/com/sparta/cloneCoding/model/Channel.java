package com.sparta.cloneCoding.model;

import com.sparta.cloneCoding.dto.ChannelRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity(name = "channel")
public class Channel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String channelName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Boolean isPrivate;

    public Channel(ChannelRequestDto requestDto){
        this.channelName = requestDto.getChannelName();
        this.description = requestDto.getDescription();
        this.isPrivate = requestDto.getIsPrivate();
    }
}
