package com.sparta.cloneCoding.service;

import com.sparta.cloneCoding.dto.ChannelRequestDto;
import com.sparta.cloneCoding.model.Channel;
import com.sparta.cloneCoding.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChannelService {
    private final ChannelRepository channelRepository;

    @Transactional
    public List<Channel> getChannelList(){
        return channelRepository.findAll();
    }

    @Transactional
    public String createChannel(ChannelRequestDto requestDto){
        Channel channel = new Channel(requestDto);
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
}
