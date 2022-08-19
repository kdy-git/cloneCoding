package com.sparta.cloneCoding.repository;

import com.sparta.cloneCoding.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, String> {
    Optional<Channel> findByChannelName(String channelName);
}
