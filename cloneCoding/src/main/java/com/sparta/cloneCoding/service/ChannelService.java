package com.sparta.cloneCoding.service;

import com.sparta.cloneCoding.dto.ChannelRequestDto;
import com.sparta.cloneCoding.model.Channel;
import com.sparta.cloneCoding.model.User;
import com.sparta.cloneCoding.repository.ChannelRepository;
import com.sparta.cloneCoding.repository.UserRepository;
import com.sparta.cloneCoding.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;    //유저정보 조회용

    @Transactional
    public List<Channel> getChannelList(){
        return channelRepository.findAll();
    }

    @Transactional
    public String createChannel(ChannelRequestDto requestDto){
        Long user_id = SecurityUtil.getCurrentUSerId();
        User user = userRepository.findById(user_id).orElseThrow(
                () -> new UsernameNotFoundException("유저정보를 찾을 수 없습니다"));

        Channel channel = new Channel(requestDto, user);
        Optional<Channel> dupleNameCheck = channelRepository.findByChannelName(channel.getChannelName());
        if(dupleNameCheck.isPresent()){
            return "해당 이름은 채널, 사용자 이름 또는 사용자 그룹에서 이미 사용 중입니다.";
        } else if(requestDto.getChannelName().isEmpty()){
            return "채널 이름을 입력하세요";
        } else{
            channelRepository.save(channel);
            return "채널 생성 완료";
        }
    }

    @Transactional
    public String deleteChannel(Long channel_id){
        Channel channel = channelRepository.findById(channel_id).orElseThrow(
                () -> new IllegalArgumentException("##### Can't find Channel/"+channel_id)
        );
        channelRepository.delete(channel);
        return "채널 삭제 성공";
    }
}
