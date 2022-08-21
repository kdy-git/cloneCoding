package com.sparta.cloneCoding.model;

import com.sparta.cloneCoding.dto.ChannelRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    // roomCreator
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "channel_id")
    private List<InviteUserChannel> inviteUserChannel;

    public Channel(ChannelRequestDto requestDto, User user){
        this.channelName = requestDto.getChannelName();
        this.description = requestDto.getDescription();
        this.user = user;
        // 채널 생성시 유저 초대하지 않아 빈 리스트 선언
        this.inviteUserChannel = new ArrayList<>();
    }

    public Channel(ChannelRequestDto channelRequestDto, List<InviteUserChannel> inviteUserChannel, User user) {
        this.channelName = channelRequestDto.getChannelName();
        this.description = channelRequestDto.getDescription();
        this.user = user;
        this.inviteUserChannel = inviteUserChannel;
    }
}
