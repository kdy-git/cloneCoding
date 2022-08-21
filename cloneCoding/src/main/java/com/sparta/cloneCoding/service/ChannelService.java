package com.sparta.cloneCoding.service;

import com.sparta.cloneCoding.dto.ChannelInviteRequestDto;
import com.sparta.cloneCoding.dto.ChannelRequestDto;
import com.sparta.cloneCoding.model.Channel;
import com.sparta.cloneCoding.model.InviteUserChannel;
import com.sparta.cloneCoding.model.User;
import com.sparta.cloneCoding.repository.*;
import com.sparta.cloneCoding.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final InviteUserChannelRepository inviteUserChannelRepository;
    private final MessageRepository messageRepository;
    

    // 채널 조회 : 채널이름, 유저목록, 채널생성자만 출력하는걸로 수정예정
    @Transactional
    public List<Channel> getChannelList(){
        return channelRepository.findAll();
    }

    // 채널 생성
    @Transactional
    public String createChannel(ChannelRequestDto requestDto){
        // 로그인한 유저 정보 확인
        User user = userRepository.findById(SecurityUtil.getCurrentUSerId()).orElseThrow(
                () -> new IllegalArgumentException("로그인이 필요합니다"));

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

    // 채널에 초대
    public String inviteChannel(ChannelInviteRequestDto channelInviteRequestDto){
        
        Channel channel = channelRepository.findById(channelInviteRequestDto.getChannelId()).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 채널입니다"));

        for(Long userId : channelInviteRequestDto.getUserList()){
            User user = userRepository.findById(userId).orElseThrow(
                    () -> new IllegalArgumentException("존재하지 않는 유저입니다"));

            // 초대 여부, 채널 생성자(본인) 검증
            if(inviteUserChannelRepository.existsByUserAndChannel(user, channel)){
                throw new IllegalArgumentException("이미 초대된 유저가 포함되어 있습니다");
            } else if(channel.getUser() == user){
                throw new IllegalArgumentException("채널 개설자는 초대할 수 없습니다");
            }
            InviteUserChannel inviteUserChannel = new InviteUserChannel(user, channel);
            inviteUserChannelRepository.save(inviteUserChannel);
        }
        return "채널 초대 성공";
    }

    @Transactional
    public String deleteChannel(Long channelId){
        Channel channel = channelRepository.findById(channelId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 채널입니다")
        );
        channelRepository.delete(channel);
        return "채널 삭제 성공";
    }
}
