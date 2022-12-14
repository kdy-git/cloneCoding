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
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MessageService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final InviteUserChannelRepository inviteUserChannelRepository;

    public MessageDto sendMessage(MessageDto messageDto, Long channelId, String token) {
        System.out.println(messageDto.getMessage());
        System.out.println("sendmessage 안에 들어옴");
        System.out.println(token);

        Authentication authentication = TokenProvider.getAuthentication(token.substring(7));
        System.out.println(authentication.getName());

        User user = userRepository.findById(Long.parseLong(authentication.getName()))
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));

        String username = user.getUsername();
        System.out.println(username);

        Channel channel = validateRole(channelId, username);
        System.out.println(channelId);

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

    public ResponseDto<MessageDto> messages(Long channelId) {
        return new ResponseDto<>(true, messageRepository
                .findTop100ByChannelIdOrderByCreatedAtDesc(channelId).stream()
                .map(MessageDto::new)
                .collect(Collectors.toList()));
    }

    private Channel validateRole(Long channelId, String username) throws IllegalArgumentException {

        System.out.println("validateRole 안");
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));

        if (username == null) {
            throw new IllegalArgumentException("로그인이 필요합니다");
        }
        System.out.println("1");

        Channel channel = channelRepository.findById(channelId).orElseThrow(() ->
                new IllegalArgumentException("채널이 존재하지 않습니다.")
        );
        System.out.println("2");

        if(!inviteUserChannelRepository.existsByChannelAndUser(channel, user)) {
            throw new IllegalArgumentException("초대되지 않은 채팅방 입니다.");
        }
        System.out.println("3");
        return channel;
    }
}
