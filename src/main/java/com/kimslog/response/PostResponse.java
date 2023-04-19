package com.kimslog.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/*
* 서비스 정채에 맞는 클래스
* */

@Getter
//@RequiredArgsConstructor
public class PostResponse {
    private Long id;
    private String title;
    private String content;

    @Builder
    public PostResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title.substring(0,Math.min(title.length(), 10));
        this.content = content;
    }
}
