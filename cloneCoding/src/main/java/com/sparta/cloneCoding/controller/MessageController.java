package com.sparta.cloneCoding.controller;

import com.sparta.cloneCoding.dto.MessageDto;
import com.sparta.cloneCoding.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/chat")
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;

    //채팅방 입장.
    @ResponseBody
    @GetMapping("/{channel_Id}")
    public ResponseEntity<?> chatRoomEntry(@PathVariable Long channel_Id) {

        return ResponseEntity.ok().body(messageService.messages(channel_Id));
    }

    //메세지 작성
    @MessageMapping(value = {"/message/{channel_Id}"})
    public void chatting(MessageDto messageDto, @DestinationVariable Long channel_Id) {

        messageDto = messageService.sendMessage(messageDto, channel_Id);
        simpMessagingTemplate.convertAndSend("/sub/channel/" + channel_Id, messageDto);
    }

}
