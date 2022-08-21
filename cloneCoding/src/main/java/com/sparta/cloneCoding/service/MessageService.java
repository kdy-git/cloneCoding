package com.sparta.cloneCoding.service;

import com.sparta.cloneCoding.dto.MessageDto;
import com.sparta.cloneCoding.dto.ResponseDto;
import com.sparta.cloneCoding.model.Channel;
import com.sparta.cloneCoding.model.Message;
import com.sparta.cloneCoding.model.User;
import com.sparta.cloneCoding.repository.ChannelRepository;
import com.sparta.cloneCoding.repository.InviteUserChannelRepository;
import com.sparta.cloneCoding.repository.MessageRepository;
import com.sparta.cloneCoding.repository.UserRepository;
import com.sparta.cloneCoding.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MessageService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final InviteUserChannelRepository inviteUserChannelRepository;

    //채팅을 입력하기 위한 메서드.
    //유효성검사 후 메세지, 유저정보를 채널에 저장
    public MessageDto sendMessage(MessageDto messageDto, Long channelId) {
        String username = userService.getMyInfo().getUsername();
        Channel channel = validateRole(channelId);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));

        messageRepository.save(Message.builder()
                .message(messageDto.getMessage())
                .user(user)
                .channel(channel)
                .build());
        messageDto.setUserId(user.getId());
        messageDto.setUsername(user.getUsername());
        messageDto.setNickname(user.getNickname());
        return messageDto;
    }

    // 채팅방 입장시 해당 채팅방의 DB저장된 메세지중 100개만 표시되도록하고, list로 return
    public ResponseDto<MessageDto> messages(Long channelId) {
        validateRole(channelId);
        return new ResponseDto<>(true, messageRepository
                .findTop100ByChannelIdOrderByCreatedAtDesc(channelId).stream()
                .map(MessageDto::new)
                .collect(Collectors.toList()));
    }

    //채널의 유효성 검사.
    private Channel validateRole(Long channelId) throws IllegalArgumentException {

        String username = userService.getMyInfo().getUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));

        if (username == null) {
            throw new IllegalArgumentException("로그인이 필요합니다");
        }

        Channel channel = channelRepository.findById(channelId).orElseThrow(() ->
                new IllegalArgumentException("채널이 존재하지 않습니다.")
        );

        if(!inviteUserChannelRepository.existsByChannelAndUser(channel, user)) {
            throw new IllegalArgumentException("초대되지 않은 채팅방 입니다.");
        }
        return channel;
    }

}
