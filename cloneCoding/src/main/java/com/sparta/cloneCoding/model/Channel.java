package com.sparta.cloneCoding.model;

import com.sparta.cloneCoding.dto.ChannelRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @Column(nullable = false)
    private Boolean isPrivate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "channel_id")
    private List<InviteUserChannel> inviteUserChannel;

    public Channel(ChannelRequestDto requestDto){
        this.channelName = requestDto.getChannelName();
        this.description = requestDto.getDescription();
        this.isPrivate = requestDto.getIsPrivate();
    }

    public Channel(ChannelRequestDto channelRequestDto, List<InviteUserChannel> inviteUserChannel, User user) {
        this.channelName = channelRequestDto.getChannelName();
        this.description = channelRequestDto.getDescription();
        this.isPrivate = channelRequestDto.getIsPrivate();
        this.user = user;
        this.inviteUserChannel = inviteUserChannel;
    }
}
