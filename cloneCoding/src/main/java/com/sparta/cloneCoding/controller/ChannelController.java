package com.sparta.cloneCoding.controller;

import com.sparta.cloneCoding.dto.ChannelRequestDto;
import com.sparta.cloneCoding.model.Channel;
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
    public List<Channel> readChannel(){
        return channelService.getChannelList();
    }

    @PostMapping("/")
    public String createChannel(@RequestBody ChannelRequestDto requestDto){
        return channelService.createChannel(requestDto);
    }
}
