package com.sparta.cloneCoding.service;

import com.sparta.cloneCoding.dto.ChannelInviteRequestDto;
import com.sparta.cloneCoding.dto.ChannelRequestDto;
import com.sparta.cloneCoding.dto.ChannelListDto;
import com.sparta.cloneCoding.dto.UserListDto;
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

@RequiredArgsConstructor
@Service
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final InviteUserChannelRepository inviteUserChannelRepository;

    @Transactional
    public List<ChannelListDto> getChannelList() {
        User user = getCurrentUser();
        List<ChannelListDto> userChannelList = new ArrayList<>();

        List<Channel> channels = channelRepository.findAllByInviteUserChannel_UserId(user.getId());
        for (Channel channel : channels) {
            Boolean isOwner = user.getId().equals(channel.getUser().getId());
            ChannelListDto channelListDto = new ChannelListDto(channel.getId(), channel.getChannelName(), channel.getDescription(), isOwner);
            userChannelList.add(channelListDto);
        }

        return userChannelList;
    }

    public List<UserListDto> getUserListInChannel (Long channelId){
        List<UserListDto> userLists = new ArrayList<>();

        List<InviteUserChannel> inviteUserChannels = inviteUserChannelRepository.findAllByChannel_Id(channelId);

        for(InviteUserChannel inviteUserChannel : inviteUserChannels){
            UserListDto userListDto = new UserListDto(inviteUserChannel.getUser().getId(),
                    inviteUserChannel.getUser().getUsername(),
                    inviteUserChannel.getUser().getNickname());
            userLists.add(userListDto);
        }

        return userLists;
    }

    @Transactional
    public ChannelListDto createChannel(ChannelRequestDto requestDto) {
        User user = getCurrentUser();
        Channel channel = new Channel(requestDto, user);

        InviteUserChannel inviteUserChannel = new InviteUserChannel(user, channel);
        inviteUserChannelRepository.save(inviteUserChannel);

        channelRepository.save(channel);

        ChannelListDto newChannelInfo = new ChannelListDto(channel.getId(), channel.getChannelName(), channel.getDescription(), true);

        return newChannelInfo;
    }

    public ChannelInviteRequestDto inviteChannel(ChannelInviteRequestDto channelInviteRequestDto) {
        User inviteUser = userRepository.findByUsername(channelInviteRequestDto.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("???????????? ?????? ???????????????"));
        Channel channel = channelRepository.findById(channelInviteRequestDto.getChannel_id()).orElseThrow(
                () -> new IllegalArgumentException("???????????? ?????? ???????????????"));

        if (inviteUserChannelRepository.existsByUserAndChannel(inviteUser, channel)) {
            throw new IllegalArgumentException("?????? ????????? ????????? ???????????? ????????????");
        } else if (channel.getUser() == inviteUser) {
            throw new IllegalArgumentException("?????? ???????????? ????????? ??? ????????????");
        }

        InviteUserChannel inviteUserChannel = new InviteUserChannel(inviteUser, channel);
        inviteUserChannelRepository.save(inviteUserChannel);

        return channelInviteRequestDto;
    }

    public String exitChannel(Long channelId) {
        User user = getCurrentUser();
        Channel channel = channelRepository.findById(channelId).orElseThrow(
                () -> new IllegalArgumentException("???????????? ?????? ???????????????"));

        if (user.getId().equals(channel.getUser().getId())) {
            channelRepository.delete(channel);
            return "?????? ?????? ??? ????????? ??????";
        } else {
            InviteUserChannel inviteUserChannel = inviteUserChannelRepository.findByUserAndChannel(user, channel);
            inviteUserChannelRepository.delete(inviteUserChannel);
            return "?????? ????????? ??????";
        }
    }

    public User getCurrentUser() {
        return userRepository.findById(SecurityUtil.getCurrentUSerId()).orElseThrow(
                () -> new IllegalArgumentException("???????????? ???????????????"));
    }
}