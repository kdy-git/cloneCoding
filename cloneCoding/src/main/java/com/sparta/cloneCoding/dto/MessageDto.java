package com.sparta.cloneCoding.dto;

import com.sparta.cloneCoding.model.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageDto {

    private Long channelId;
    private String message;
    private long createdAt;
    private Long userId;
    private String username;
    private String nickname;

    public MessageDto(Message message) {
        this.channelId = message.getChannel().getId();
        this.message = message.getMessage();
        this.createdAt = message.getCreatedAt();
        this.userId = message.getUser().getId();
        this.username = message.getUser().getUsername();
        this.nickname = message.getUser().getNickname();

    }

    public MessageDto(Long channelId, String message){
        this.channelId = channelId;
        this.message = message;
    }
}
