package com.kimslog.exception;

public class PostNotfound extends RuntimeException{

    private static final String MESSAGE = "존재하지 않는 글입니다.";
    //생성자 오버로딩
    public PostNotfound() {
        super(MESSAGE);
    }
}
