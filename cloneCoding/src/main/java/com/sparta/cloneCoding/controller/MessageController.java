package com.sparta.cloneCoding.controller;

import com.sparta.cloneCoding.dto.MessageDto;
import com.sparta.cloneCoding.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;

    @ResponseBody
    @GetMapping("/entry/{channel_Id}")
    public ResponseEntity<?> chatRoomEntry(@PathVariable Long channel_Id) {

        return ResponseEntity.ok().body(messageService.messages(channel_Id));
    }

    @MessageMapping("/message/{channel_Id}")
    public MessageDto chatting(@RequestBody MessageDto messageDto, @DestinationVariable Long channel_Id, @Header(value = "Authorization") String token) {
        try {

            System.out.println(token);
            System.out.println("channelId : " + channel_Id);

            MessageDto responseMessageDto = messageService.sendMessage(messageDto, channel_Id, token);

            System.out.println("토큰 받아왔을 때");
            System.out.println("username : " + responseMessageDto.getUsername());

            simpMessagingTemplate.convertAndSend("/sub/message/" + channel_Id, responseMessageDto);

            System.out.println("정상 실행 되었을 때");
            return responseMessageDto;

        } catch (Exception e) {
            System.out.println("catch 안에 들어왔을 때");
            e.printStackTrace();
            return null;
        }
    }

}
