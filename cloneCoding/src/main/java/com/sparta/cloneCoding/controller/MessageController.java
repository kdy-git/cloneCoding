package com.sparta.cloneCoding.controller;

import com.sparta.cloneCoding.dto.MessageDto;
import com.sparta.cloneCoding.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;

    //채팅방 입장.
    @ResponseBody
    @GetMapping("/entry/{channel_Id}")
    public ResponseEntity<?> chatRoomEntry(@PathVariable Long channel_Id) {

        return ResponseEntity.ok().body(messageService.messages(channel_Id));
    }

//    //send 버튼 눌렀을 때 destination: pub/message/10
//    @MessageMapping("/message/{channel_Id}")
//    public void getMessage(@RequestBody MessageDto message, @DestinationVariable Long channel_Id){
//        System.out.println(message.getMessage());
//
//        MessageDto messageDto = new MessageDto(channel_Id, message.getMessage());
//        System.out.println("dto에 담긴 값 : " + messageDto.getMessage());
//        simpMessagingTemplate.convertAndSend("/sub/message/"+channel_Id, messageDto);
//    }

    //메세지 작성
    @MessageMapping("/message/{channel_Id}")
    public MessageDto chatting(@RequestBody MessageDto messageDto, @DestinationVariable Long channel_Id) {
        try {
            System.out.println("channelId : " + channel_Id);

            System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
            messageDto = messageService.sendMessage(messageDto, channel_Id);
            System.out.println("username : " + messageDto.getUsername());
            System.out.println("토큰 받아왔을 때");

            simpMessagingTemplate.convertAndSend("/sub/message/" + channel_Id, messageDto);

            System.out.println("정상 실행 되었을 때");
            return messageDto;

        }catch(Exception e) {
            System.out.println("catch 안에 들어왔을 때");
            return messageDto;
        }
    }

}
