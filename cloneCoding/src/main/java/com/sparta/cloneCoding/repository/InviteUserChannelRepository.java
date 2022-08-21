package com.sparta.cloneCoding.repository;


import com.sparta.cloneCoding.model.Channel;
import com.sparta.cloneCoding.model.User;
import com.sparta.cloneCoding.model.InviteUserChannel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteUserChannelRepository extends JpaRepository<InviteUserChannel, Long> {

    boolean existsByChannelAndUser(Channel channel, User user);

    InviteUserChannel findByUserAndChannel(User user, Channel channel);

    boolean existsByUserAndChannel(User user, Channel channel);

    boolean existsByUserAndChannelId(User user, Long channelId);

}

