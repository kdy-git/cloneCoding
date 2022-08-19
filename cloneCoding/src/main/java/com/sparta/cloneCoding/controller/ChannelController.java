package com.sparta.cloneCoding.controller;

import com.sparta.cloneCoding.dto.ChannelRequestDto;
import com.sparta.cloneCoding.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ChannelController {

    private final ChannelService channelService;

    @PostMapping("/channel")
    public String createChannel(@RequestBody ChannelRequestDto requestDto){
        return channelService.createChannel(requestDto);
    }
}
