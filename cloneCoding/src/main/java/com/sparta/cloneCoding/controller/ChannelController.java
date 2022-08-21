package com.sparta.cloneCoding.controller;

import com.sparta.cloneCoding.dto.ChannelInviteRequestDto;
import com.sparta.cloneCoding.dto.ChannelRequestDto;
import com.sparta.cloneCoding.dto.ChannelListDto;
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

    // 채널 생성
    @PostMapping("")
    public String createChannel(@RequestBody ChannelRequestDto requestDto){
        return channelService.createChannel(requestDto);
    }

    // 채널 삭제
    @DeleteMapping("/{channelId}")
    public String deleteChannel(@PathVariable Long channelId){
        return channelService.deleteChannel(channelId);
    }

    // 채널 초대
    @PostMapping("/invite")
    public String inviteChannel(@RequestBody ChannelInviteRequestDto channelInviteRequestDto){
        return channelService.inviteChannel(channelInviteRequestDto);
    }
}
