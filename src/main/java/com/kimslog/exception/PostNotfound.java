package com.kimslog.exception;
/*
 * status -> 404
 *
 */
public class PostNotfound extends KimslogException{

    private static final String MESSAGE = "존재하지 않는 글입니다.";
    //생성자 오버로딩
    public PostNotfound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
