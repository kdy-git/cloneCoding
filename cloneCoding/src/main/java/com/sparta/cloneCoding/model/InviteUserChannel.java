package com.sparta.cloneCoding.model;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class InviteUserChannel {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

}
