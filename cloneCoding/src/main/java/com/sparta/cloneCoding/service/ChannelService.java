package com.sparta.cloneCoding.service;

import com.sparta.cloneCoding.dto.ChannelInviteRequestDto;
import com.sparta.cloneCoding.dto.ChannelRequestDto;
import com.sparta.cloneCoding.dto.ChannelListDto;
import com.sparta.cloneCoding.model.Channel;
import com.sparta.cloneCoding.model.InviteUserChannel;
import com.sparta.cloneCoding.model.User;
import com.sparta.cloneCoding.repository.*;
import com.sparta.cloneCoding.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final InviteUserChannelRepository inviteUserChannelRepository;
    private final MessageRepository messageRepository;
    

    // 채널 조회
    @Transactional
    public List<ChannelListDto> getChannelList(){
        User user = getCurrentUser();
        List<ChannelListDto> userChannelList = new ArrayList<>();

        // 채널 생성시 inviteUserChannel에 본인도 넣을것인지? 넣는다면 어떻게?
        // 본인도 넣는다면 생성한 채널 추가부분 필요없음
        //생성한 채널 추가
        List<Channel> channels = channelRepository.findAllByUser_Id(user.getId());
        for(Channel channel:channels){
            ChannelListDto channelListDto = new ChannelListDto(channel.getId(), channel.getChannelName(), channel.getDescription(), true);
            userChannelList.add(channelListDto);
        }

        //초대된 채널 추가
        channels = channelRepository.findAllByInviteUserChannel_UserId(user.getId());
        for(Channel channel:channels){
            ChannelListDto channelListDto = new ChannelListDto(channel.getId(), channel.getChannelName(), channel.getDescription(), false);
            userChannelList.add(channelListDto);
        }

        return userChannelList;
    }

    // 채널 생성
    @Transactional
    public String createChannel(ChannelRequestDto requestDto){
        User user = getCurrentUser();

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

    // 채널 삭제 (채널 생성자만 가능, 삭제시 채널에 참가했던 유저 리스트도 삭제)
    @Transactional
    public String deleteChannel(Long channelId){
        Channel channel = channelRepository.findById(channelId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 채널입니다")
        );
        channelRepository.delete(channel);
        return "채널 삭제 성공";
    }

    // 채널 나가기 (채널 생성자의 경우 나가기 시 채널 삭제)
    public String exitChannel(Long channelId){
        User user = getCurrentUser();
        Channel channel = channelRepository.findById(channelId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 채널입니다"));

        if(user.getId().equals(channel.getUser().getId())){
            // 채널 생성자의 경우 채널 및 초대된 유저들도 나가기(cascade)
            channelRepository.delete(channel);
            return "채널 삭제 및 나가기 성공";
        } else{
            InviteUserChannel inviteUserChannel = inviteUserChannelRepository.findByUserAndChannel(user, channel);
            inviteUserChannelRepository.delete(inviteUserChannel);
            return "채널 나가기 성공";
        }

    }

    public User getCurrentUser(){
        return userRepository.findById(SecurityUtil.getCurrentUSerId()).orElseThrow(
                () -> new IllegalArgumentException("로그인이 필요합니다"));
    }
}
