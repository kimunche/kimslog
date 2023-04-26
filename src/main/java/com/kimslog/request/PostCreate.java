package com.kimslog.request;

import com.kimslog.exception.InvalidRequest;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
//@AllArgsConstructor //생성자 생성
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해주세요.")
    private String title;

    private String content;

    @Builder
    public PostCreate(String content, String title){
        this.title = title;
        this.content = content;
    }

    public void validate(){
        if(title.contains("바보")){
            throw new InvalidRequest("title", "제목에 바보를 포함할 수 없습니다.");
        }
    }
}
