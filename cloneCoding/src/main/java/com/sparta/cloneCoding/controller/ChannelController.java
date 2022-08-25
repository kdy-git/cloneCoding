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

    @GetMapping("/list")
    public List<ChannelListDto> readChannel(){
        return channelService.getChannelList();
    }

    @GetMapping("/userlist/{channelId}")
    public List<UserListDto> readUserInChannel(@PathVariable Long channelId) {
        return channelService.getUserListInChannel(channelId);
    }

    @PostMapping("")
    public ChannelListDto createChannel(@RequestBody ChannelRequestDto requestDto){
        return channelService.createChannel(requestDto);
    }

    @PostMapping("/invite")
    public ChannelInviteRequestDto inviteChannel(@RequestBody ChannelInviteRequestDto channelInviteRequestDto){
        return channelService.inviteChannel(channelInviteRequestDto);
    }

    @DeleteMapping("/exit/{channelId}")
    public String exitChannel(@PathVariable Long channelId){
        return channelService.exitChannel(channelId);
    }
}
