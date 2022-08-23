package com.sparta.cloneCoding.controller;

import com.sparta.cloneCoding.dto.ChannelInviteRequestDto;
import com.sparta.cloneCoding.dto.ChannelRequestDto;
import com.sparta.cloneCoding.dto.ChannelListDto;
import com.sparta.cloneCoding.dto.UserListDto;
import com.sparta.cloneCoding.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/channel")
@RestController
public class ChannelController {

    private final ChannelService channelService;

    // 로그인한 유저의 채널 목록 조회
    @GetMapping("/list")
    public List<ChannelListDto> readChannel(){
        return channelService.getChannelList();
    }

    // 해당 채널에 있는 유저 목록 조회
    @GetMapping("/userlist/{channelId}")
    public List<UserListDto> readUserInChannel(@PathVariable Long channelId) {
        return channelService.getUserListInChannel(channelId);
    }

    // 채널 생성
    @PostMapping("")
    public ChannelListDto createChannel(@RequestBody ChannelRequestDto requestDto){
        return channelService.createChannel(requestDto);
    }

    // 채널 초대
    @PostMapping("/invite")
    public ChannelInviteRequestDto inviteChannel(@RequestBody ChannelInviteRequestDto channelInviteRequestDto){
        return channelService.inviteChannel(channelInviteRequestDto);
    }

    // 채널 나가기
    @DeleteMapping("/exit/{channelId}")
    public String exitChannel(@PathVariable Long channelId){
        return channelService.exitChannel(channelId);
    }
}
