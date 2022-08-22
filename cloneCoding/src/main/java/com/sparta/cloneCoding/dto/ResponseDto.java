package com.sparta.cloneCoding.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {
    private boolean response;
    private String message;
    private List<T> list;
    private Long userId;
    private String username;
    private String nickname;
    public ResponseDto(boolean response, String message,List<T> userChannelList) {
        this.response = response;
        this.message = message;
        this.list = userChannelList;
    }
    public ResponseDto(boolean response,List<T> list) {
        this.response = response;
        this.list = list;
    }
}