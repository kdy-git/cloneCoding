package com.sparta.cloneCoding.repository;

import com.sparta.cloneCoding.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, String> {
    Optional<Channel> findByChannelName(String channelName);
    Optional<Channel> findById(Long channel_id);

    List<Channel> findAllByUser_Id(Long user_id);
    List<Channel> findAllByInviteUserChannel_UserId(Long user_id);
}
