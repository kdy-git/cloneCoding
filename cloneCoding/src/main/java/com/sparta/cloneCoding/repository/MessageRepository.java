package com.sparta.cloneCoding.repository;

import com.sparta.cloneCoding.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findTop100ByChannelIdOrderByCreatedAtDesc(Long channelId);

}